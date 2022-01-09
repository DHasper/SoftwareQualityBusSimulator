package hanze.nl.bussimulator;

import java.util.ArrayList;

import hanze.nl.bussimulator.buslijn.ETA;

public class Bericht {
	String lijnNaam;
	public String eindpunt;
	String bedrijf;
	String busID;
	int tijd;
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
