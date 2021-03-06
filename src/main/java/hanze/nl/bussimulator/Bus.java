package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.Halte.Positie;
import hanze.nl.shared.Bericht;
import hanze.nl.shared.ETA;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	public void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	public void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	public boolean halteBereikt(){
		halteNummer+=richting;
		bijHalte=true;
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
			return true;
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
			naarVolgendeHalte();
		}		
		return false;
	}
	
	public void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
		naarVolgendeHalte();
	}

	public boolean move(){
		boolean eindpuntBereikt = false;
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			totVolgendeHalte--;
			if (totVolgendeHalte==0){
				eindpuntBereikt=halteBereikt();
			}
		}
		return eindpuntBereikt;
	}

	public Bericht addPropertiesToMessage(Bericht bericht, int nu) {
		// If we are currently at a stop, add an ETA of 0 seconds to the message.
		if (bijHalte)
			bericht.ETAs.add(
				new ETA(lijn.getHalte(halteNummer).name(), lijn.getRichting(halteNummer), 0));

		Positie eerstVolgende = lijn.getHalte(halteNummer + richting).getPositie();
		int tijdNaarHalte = totVolgendeHalte + nu;

		int i = 0;
		for (i = halteNummer + richting; i < lijn.getLengte() && i >= 0; i = i + richting){
			tijdNaarHalte += lijn.getHalte(i).afstand(eerstVolgende);
			bericht.ETAs.add(new ETA(lijn.getHalte(i).name(), lijn.getRichting(i), tijdNaarHalte));
			eerstVolgende = lijn.getHalte(i).getPositie();
		}

		bericht.eindpunt = lijn.getHalte(i - richting).name();

		return bericht;
	}
	
	public void sendETAs(int nu){
		// Create new message.
		Bericht bericht = new Bericht(lijn.name(), bedrijf.name(), busID, nu);

		// Decorate message with information.
		bericht = addPropertiesToMessage(bericht, nu);

		sendBericht(bericht);
	}
	
	public void sendLastETA(int nu){
		Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		ETA eta = new ETA(eindpunt,lijn.getRichting(halteNummer),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		sendBericht(bericht);
	}

	public void sendBericht(Bericht bericht){
    	XStream xstream = new XStream();
    	xstream.alias("Bericht", Bericht.class);
    	xstream.alias("ETA", ETA.class);
    	String xml = xstream.toXML(bericht);
    	Producer producer = new Producer();
    	producer.sendBericht(xml);		
	}
}
