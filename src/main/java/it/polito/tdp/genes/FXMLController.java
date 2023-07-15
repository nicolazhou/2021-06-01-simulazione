/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Adiacente;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	this.model.buildGraph();
    	
    	this.txtResult.setText("Grafo creato con " + this.model.getNVertici() + " vertici e " + this.model.getNArchi() + " archi \n");
    	
    	this.cmbGeni.getItems().clear();
    	this.cmbGeni.getItems().addAll(this.model.getGraphGenes());

    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {

    	if(!this.model.isGrafoLoaded()) {
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    	}
    	
    	if(this.txtResult.getText().compareTo("Scegli un gene")==0) {
    		this.txtResult.clear();
    	}
    	
    	Genes genes = this.cmbGeni.getValue();
    	
    	if(genes == null) {
    		this.txtResult.setText("Scegli un gene");
    		return;
    	}
    	
    	
    	List<Adiacente> risultato = this.model.geniAdiacenti(genes);
    	
    	this.txtResult.appendText("\nGeni adiacenti a: " + genes + "\n");
    	for(Adiacente a : risultato) {
    		this.txtResult.appendText(a.toString() + "\n");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
      	if(!this.model.isGrafoLoaded()) {
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    	}
    	
    	Genes genes = this.cmbGeni.getValue();
    	
    	if(genes == null) {
    		this.txtResult.setText("Scegli un gene");
    		return;
    	}
      	
    	String input = this.txtIng.getText();
    	
    	int n = 0;
    	
    	try {
    		
    		n = Integer.parseInt(input);
    		
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valore numerico al numero di ingegneri!");
    		return;
    	}
    	
    	
    	Map<Genes, Integer> mappa = this.model.Simula(genes, n);
    	
    	this.txtResult.appendText("Simulazione: \n");
    	
    	for(Genes g : mappa.keySet()) {
    		
    		this.txtResult.appendText(g + " : " + mappa.get(g) + "\n");
    		
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
