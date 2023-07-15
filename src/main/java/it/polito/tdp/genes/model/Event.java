package it.polito.tdp.genes.model;

public class Event implements Comparable<Event>{

	private int t;
	private int nIngegnere; // Numero ingegnere
	
	
	public Event(int t, int nIngegnere) {
		super();
		this.t = t;
		this.nIngegnere = nIngegnere;
	}
	
	
	public int getT() {
		return t;
	}
	
	
	public int getnIngegnere() {
		return nIngegnere;
	}


	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.t - o.getT();
	}
	
	
	
	
}
