package ar.edu.utn.dds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Condicion2 implements Initializable{
	
	private Stage stagePrincipal;
    
	public void setStagePrincipal2(Stage stagePrincipal) {
	       this.stagePrincipal = stagePrincipal;
	 }
	
	@FXML
    private Button idCerrar;

    @FXML
    private TextField idLadoIzq;

    @FXML
    private TextField idPeriodos;

    @FXML
    private TextField idCriterio;

    @FXML
    void criterio(ActionEvent event) {

    }

    @FXML
    void ladoIzq(ActionEvent event) {

    }

    @FXML
    void periodos(ActionEvent event) {

    }	
	@FXML
    void cerrar(ActionEvent event) {
	  	stagePrincipal.close();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

}
