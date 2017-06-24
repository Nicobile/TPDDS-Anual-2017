package ar.edu.utn.dds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import ar.edu.utn.dds.interfazGrafica.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



public class MainGraf implements Initializable {

	private Main ProgramaPrincipal;
	
	public void setProgramaPrincipal(Main ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
	@FXML
    private Button idBtnCerrar;

    @FXML
    private Button idBtnAgregar;

    @FXML
    private ComboBox<String> idBtnCondicion;

    @FXML
    private TextField idTextMetod;

    @FXML
    private void btnAgregar(ActionEvent event) {
    	
    	if (idBtnCondicion.getValue().equals("Condicion1")){
    		ProgramaPrincipal.mostrarCondicion1();
    	}
    	if (idBtnCondicion.getValue().equals("Condicion2")){
    		ProgramaPrincipal.mostrarCondicion2();
    	}
    	if (idBtnCondicion.getValue().equals("Condicion3")){
    		ProgramaPrincipal.mostrarCondicion3();
    	}

    }

    @FXML
    private void btnCerrar(ActionEvent event) {

    }

    @FXML
    void btnCondicion(ActionEvent event) {
    	
    }

    @FXML
    void textMetod(ActionEvent event) {

    }
    
    
    
	 @Override
	 public void initialize(URL location,ResourceBundle resource){
		 
		ObservableList<String> condiciones = FXCollections.observableArrayList("Condicion1","Condicion2","Condicion3");
		
		idBtnCondicion.setItems(condiciones);
		idBtnCondicion.getSelectionModel().select(0);
		
		
	
		//idBtnCondicion.getItems().add("Condicion1");
		//idBtnCondicion.getItems().add("Condicion2");
		//idBtnCondicion.getItems().add("Condicion3");
		
	 }

}
