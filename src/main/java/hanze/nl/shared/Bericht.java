package hanze.nl.shared;

import java.util.ArrayList;

public class Bericht {
	public String lijnNaam;
	public String eindpunt;
	public String bedrijf;
	public String busID;
	public int tijd;
	public ArrayList<ETA> ETAs;
	
	public Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<ETA>();
	}
}
