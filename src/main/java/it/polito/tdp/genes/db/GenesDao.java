package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Genes> getGenesVertici(){
		String sql = "SELECT DISTINCT(GeneID), Essential, Chromosome "
				+ "FROM genes "
				+ "WHERE Essential = \"Essential\" "
				+ "ORDER BY GeneID ASC";
		
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Arco> getArchi(Map<String, Genes> idMap){
		String sql = "SELECT GeneID1, GeneID2, Expression_Corr as corr "
				+ "FROM interactions "
				+ "WHERE GeneID1 <> GeneID2 AND GeneID1 IN (SELECT DISTINCT(GeneID) FROM genes WHERE Essential = 'Essential') "
				+ "AND GeneID2 IN (SELECT DISTINCT(GeneID) FROM genes WHERE Essential = 'Essential')";
		
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Genes genes1 = idMap.get(res.getString("GeneID1"));
				Genes genes2 = idMap.get(res.getString("GeneID2"));
				
				double corr = res.getDouble("corr");
				
				if(corr < 0) {
					corr = corr*(-1);
				}
				
				result.add(new Arco(genes1, genes2, corr));
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
