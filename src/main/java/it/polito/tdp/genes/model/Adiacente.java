package it.polito.tdp.genes.model;

public class Adiacente implements Comparable<Adiacente>{
	
	private Genes genes;
	private Double peso;
	
	
	public Adiacente(Genes genes, Double peso) {
		super();
		this.genes = genes;
		this.peso = peso;
	}


	public Genes getGenes() {
		return genes;
	}


	public Double getPeso() {
		return peso;
	}


	@Override
	public String toString() {
		return this.genes.getGeneId() + " " + this.peso;
	}


	@Override
	public int compareTo(Adiacente o) {
		// TODO Auto-generated method stub
		return -(this.peso.compareTo(o.getPeso()));
	}
	
	
	
	
}
