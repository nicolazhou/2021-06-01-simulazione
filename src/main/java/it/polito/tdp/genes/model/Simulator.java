package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	
	// Modello del sistema
	List<Genes> genesStudiati;
	
	
	// Dati in input
	private int T_MAX = 36; // 3 anni
	
	private Genes startGene ;
	private int nTotIng ;
	private Graph<Genes, DefaultWeightedEdge> grafo ;
	
	
	
	// Dati in output
	// genesStudiati
	private Map<Genes, Integer> geniNumeroIngeneri;
	
	
	
	// Coda degli eventi
	private PriorityQueue<Event> queue;



	public Simulator(Genes startGene, int nTotIng, Graph<Genes, DefaultWeightedEdge> grafo) {
		super();
		this.startGene = startGene;
		this.nTotIng = nTotIng;
		this.grafo = grafo;
		
		this.genesStudiati = new ArrayList<>();
		
		this.geniNumeroIngeneri = new HashMap<>();
		
		if(this.grafo.degreeOf(this.startGene)==0) {
			throw new IllegalArgumentException("Vertice di partenza isolato") ;
		}
		
		this.queue = new PriorityQueue<>();
		
	}
	
	public void init() {
		
		for(int i = 0; i < this.nTotIng; i++) {
			
			this.queue.add(new Event(0, i));
			
		}
		
		// inizializza mondo, creando un array con nTotIng valori pari a startGene
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			this.genesStudiati.add(startGene);
		}
		
	}
	
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			
			Event e = this.queue.poll();
			
			System.out.println(e);
			
			int t = e.getT();
			int nIng = e.getnIngegnere();
			Genes geneStudiato = this.genesStudiati.get(nIng);
			
			if(t < this.T_MAX) {
				
				if(Math.random() < 0.3) { // 30% --> Studia lo stesso gene
				
					this.queue.add(new Event(t+1, nIng));
				
				} else { // Cambia gene
					
					// calcola la somma dei pesi degli adiacenti, S
					double S = 0 ;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(geneStudiato)) {
						S += this.grafo.getEdgeWeight(edge) ;
					}
					
					// estrai numero casuale R tra 0 e S
					double R = Math.random() * S ;
					
					// confronta R con le somme parziali dei pesi
					Genes nuovo = null ;
					double somma = 0.0 ;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(geneStudiato)) {
						somma += this.grafo.getEdgeWeight(edge) ;
						if(somma > R) {
							nuovo = Graphs.getOppositeVertex(this.grafo, edge, geneStudiato) ;
							break ;
						}
					}
					
					this.genesStudiati.set(nIng, nuovo);
					this.queue.add(new Event(t+1, nIng)) ;
					
					
				}
				
				
			}
			
			
		}
		
		
	}

	public Map<Genes, Integer> getGeniNumeroIngeneri() {
		return geniNumeroIngeneri;
	}
	
	
}
