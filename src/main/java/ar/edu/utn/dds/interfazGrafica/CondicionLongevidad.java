package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.ResourceBundle;

import ar.edu.utn.dds.modelo.Metodologia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CondicionLongevidad implements Initializable {

	private Stage stagePrincipal;
	private Metodologia metodologia;

	@FXML
	private TextField idAnios;

	@FXML
	private Button idCargar;

	@FXML
	private Button idCerrar;

	@FXML
	void anios(ActionEvent event) {
		
	}

	@FXML
	void cargar(ActionEvent event) {
       
	}

	@FXML	
	void cerrar(ActionEvent event) {

	}

	public void setMetodologia(Metodologia metod) {
		metodologia = metod;

	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
