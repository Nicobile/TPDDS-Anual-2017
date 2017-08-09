package ar.edu.utn.dds.interfazGrafica;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Condicion1 implements Initializable {

	private Stage stagePrincipal;

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	@FXML
	private BorderPane idBorderPane;

	@FXML
	private Button idCerrar;

	@FXML
	private TextField idLadoDerecho;

	@FXML
	private TextField idComparador;

	@FXML
	private TextField idPeriodos;

	@FXML
	private Button idCargar;

	@FXML
	private ComboBox<String> idLadoIzq;

	@FXML
	void cargar(ActionEvent event) {
		if (idLadoIzq.getValue().equals("Longevidad")) {

		}
		if (idLadoIzq.getValue().equals("Creciente")) {

		}
		if (idLadoIzq.getValue().equals("Decreciente")) {

		}
	}

	@FXML
	void comparador(ActionEvent event) {

	}

	@FXML
	void ladoderecho(ActionEvent event) {

	}

	@FXML
	void ladoizq(ActionEvent event) {

	}

	@FXML
	void periodos(ActionEvent event) {

	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	@FXML
	void ladoIzq(ActionEvent event) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		ObservableList<String> ladoIzq = FXCollections.observableArrayList("Longevidad", "Creciente", "Decreciente");

		idLadoIzq.setItems(ladoIzq);
		idLadoIzq.getSelectionModel().select(0);
	}

}
