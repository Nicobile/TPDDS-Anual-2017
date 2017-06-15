package ar.edu.utn.dds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MainGraf implements Initializable {

	
	@FXML
    private Button idBtnCerrar;

    @FXML
    private Button idbtnContinuar;

    @FXML
    private ComboBox<String> idBtnCondicion;

    @FXML
    void btnCerrar(ActionEvent event) {

    }

    @FXML
    void btnCondicion(ActionEvent event) {

    }

    @FXML
    void btnContinuar(ActionEvent event) {

    }
	 @Override
	 public void initialize(URL location,ResourceBundle resource){
		idBtnCondicion.getItems().add("Condicion1");
		idBtnCondicion.getItems().add("Condicion2");
		idBtnCondicion.getItems().add("Condicion3");
	 }
}
