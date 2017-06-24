package ar.edu.utn.dds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Condicion1 implements Initializable{
	
	private Stage stagePrincipal;
	    
	public void setStagePrincipal(Stage stagePrincipal) {
	       this.stagePrincipal = stagePrincipal;
	 }
	
    @FXML
    private BorderPane idBorderPane;

    @FXML
    private Button idCerrar;

    @FXML
    private TextField idLadoIzq;

    @FXML
    private TextField idLadoDerecho;

    @FXML
    private TextField idComparador;

    @FXML
    private TextField idPeriodos;

    @FXML
    void cerrar(ActionEvent event) {
    	stagePrincipal.close();
    }

    @FXML
    void comparador(ActionEvent event) {

    }

    @FXML
    void ladoIzq(ActionEvent event) {

    }

    @FXML
    void ladoderecho(ActionEvent event) {

    }

    @FXML
    void periodos(ActionEvent event) {

    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

}

