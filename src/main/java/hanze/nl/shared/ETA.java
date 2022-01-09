package hanze.nl.shared;

public class ETA {
	public String halteNaam;
	public int richting;
	public int aankomsttijd;
	
	public ETA(String halteNaam, int richting, int aankomsttijd){
		this.halteNaam=halteNaam;
		this.richting=richting;
		this.aankomsttijd=aankomsttijd;
	}

	public String getHalteNaam() {
		return halteNaam;
	}

	public void setHalteNaam(String halteNaam) {
		this.halteNaam = halteNaam;
	}

	public int getRichting() {
		return richting;
	}

	public void setRichting(int richting) {
		this.richting = richting;
	}

	public int getAankomsttijd() {
		return aankomsttijd;
	}

	public void setAankomsttijd(int aankomsttijd) {
		this.aankomsttijd = aankomsttijd;
	}
}
