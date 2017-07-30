package ar.edu.utn.dds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Condicion3 implements Initializable{
	
	private Stage stagePrincipal;
    
	public void setStagePrincipal3(Stage stagePrincipal) {
	       this.stagePrincipal = stagePrincipal;
	 }
	 @FXML
	 private Button idCerrar;


	 @FXML
	 private TextField idPeriodos;
	 
	 @FXML
	 private ComboBox<String> idLadoIzq;

	 @FXML
	 void cerrar(ActionEvent event) {
			stagePrincipal.close();
	 }

	 @FXML
	 void ladoIzq(ActionEvent event) {

	 }

	 @FXML
	 void periodos(ActionEvent event) {

	 }

    @FXML
    private Button idCargar;



    @FXML
    void cargar(ActionEvent event) {
    	
    	if (idLadoIzq.getValue().equals("Longevidad")){
    
    	}
    	if (idLadoIzq.getValue().equals("Creciente")){

    	}
    	if (idLadoIzq.getValue().equals("Decreciente")){
    
    	}
    }

	 
    public void initialize(URL url, ResourceBundle rb) {
	       // TODO
    	ObservableList<String> ladoIzq = FXCollections.observableArrayList("Longevidad","Creciente","Decreciente");
		
		idLadoIzq.setItems(ladoIzq);
		idLadoIzq.getSelectionModel().select(0);
    } 
}
