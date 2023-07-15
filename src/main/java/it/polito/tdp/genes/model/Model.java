package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	
	private List<Genes> allGenes;
	private Map<String, Genes> idGenesMap;
	
	private Graph<Genes, DefaultWeightedEdge> graph;
	
	public Model() {
		
		this.dao = new GenesDao();
		
		this.idGenesMap = new HashMap<>();
		
	}
	
	
	public void buildGraph() {
		
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		// Nodi
		this.allGenes = new ArrayList<>(this.dao.getGenesVertici());
		
		for(Genes g : this.allGenes) {
			this.idGenesMap.put(g.getGeneId(), g);
		}
		
		Graphs.addAllVertices(this.graph, this.allGenes);
		
		
		// Archi
		for(Arco a : this.dao.getArchi(this.idGenesMap)) {
			
			if(a.getGenes1().getChromosome() == a.getGenes2().getChromosome()) { // Cromosoma uguale
				
				Graphs.addEdgeWithVertices(this.graph, a.getGenes1(), a.getGenes2(), 2.0*a.getCorr());
				
			} else {
				
				Graphs.addEdgeWithVertices(this.graph, a.getGenes1(), a.getGenes2(), a.getCorr());
				
			}
			
			
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# Vertici: " + this.graph.vertexSet().size());
		System.out.println("# Archi: " + this.graph.edgeSet().size());
	}
	
	
	public List<Adiacente> geniAdiacenti(Genes genes) {
		
		List<Genes> vicini = Graphs.neighborListOf(this.graph, genes);
		
		List<Adiacente> result = new ArrayList<>();
		
		for(Genes g : vicini) {
			
			DefaultWeightedEdge e = this.graph.getEdge(genes, g);
			
			Adiacente a = new Adiacente(g, this.graph.getEdgeWeight(e));
			
			result.add(a);
			
		}
		
		Collections.sort(result);
		
		return result;
		
	}
	
	public boolean isGrafoLoaded() {
		
		if(this.graph == null)
			return false;
		
		return true;
	}
	
	
	
	public List<Genes> getGraphGenes() {
		return this.allGenes;
	}
	
	
	public int getNVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.graph.edgeSet().size();
	}
	
	
	public Map<Genes, Integer> Simula(Genes startGene, int nIng) {
		
		Simulator sim = new Simulator(startGene, nIng, this.graph);
		
		sim.init();
		sim.run();
		
		return sim.getGeniNumeroIngeneri();
		
		
	}
	
}
