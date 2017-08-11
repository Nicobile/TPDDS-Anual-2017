package ar.edu.utn.dds.interfazGrafica;
import java.net.URL;
import java.util.ResourceBundle;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Traductor;
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

	private Traductor t;
	private String texto;
	private Metodologia metod; // primero cargar la metodologia

	@FXML
	private Button idBtnCerrar;

	@FXML
	private Button idBtnSeleccionar;

	@FXML
	private ComboBox<String> idBtnCondicion;

	@FXML
	private TextField idTextMetod;

	@FXML
	private Button idCargar;

	@FXML
	void cargar(ActionEvent event) {


		metod = new Metodologia(idTextMetod.getText());
		t.agregarMetodologia(metod);
	}

	@FXML
	private void btnSeleccionar(ActionEvent event)  {
		
		
			ProgramaPrincipal.mostrarCondicion(metod,idBtnCondicion.getValue());
	
	}

	@FXML
	void btnCerrar(ActionEvent event) {
		
	}

	@FXML
	void btnCondicion(ActionEvent event) {
	}

	@FXML
	void textMetod(ActionEvent event) {
	}

	/* JAVA */

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		ObservableList<String> condiciones = FXCollections.observableArrayList("Longevidad", "Creciente",
				"Decreciente","Mediana","Promedio","Sumatoria");

		idBtnCondicion.setItems(condiciones);
		idBtnCondicion.getSelectionModel().select(0);


	}

	public void setProgramaPrincipal(Main ProgramaPrincipal) {
		this.ProgramaPrincipal = ProgramaPrincipal;
	}

	public void setTraductor(Traductor tradu) {
		this.t = tradu;
	}

	public String getNombre() {
		return this.texto;
	}
}
