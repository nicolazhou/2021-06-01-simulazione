package it.polito.tdp.genes.model;

public class Arco {
	
	private Genes genes1;
	private Genes genes2;
	
	private double corr;

	public Arco(Genes genes1, Genes genes2, double corr) {
		super();
		this.genes1 = genes1;
		this.genes2 = genes2;
		this.corr = corr;
	}

	public Genes getGenes1() {
		return genes1;
	}

	public Genes getGenes2() {
		return genes2;
	}

	public double getCorr() {
		return corr;
	}
	
	
	
}
