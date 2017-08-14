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
import javafx.stage.Stage;

public class MenuPrincipal implements Initializable {

	private Main ProgramaPrincipal;
	private Stage stagePrincipalMenuP;
	@FXML
	private ComboBox<String> idOpciones;

	@FXML
	private Button idIngresar;
	
	@FXML
    private Button idCerrar;

	@FXML
	void ingresar(ActionEvent event) {
		if (idOpciones.getValue().equals("Cargar Empresas y Cuentas")) {
			ProgramaPrincipal.menuCuentas();

		}
		if (idOpciones.getValue().equals("Menu Metodologia")) {
			ProgramaPrincipal.mostrarMenuMetodologias();

		}
		if (idOpciones.getValue().equals("Menu Indicadores")) {
			ProgramaPrincipal.menuIndicadores();
		}
	}

	@FXML
	void opciones(ActionEvent event) {

	}
	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipalMenuP.close();
	}

	/* JAVA */

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		ObservableList<String> menu = FXCollections.observableArrayList("Cargar Empresas y Cuentas", "Menu Metodologia",
				"Menu Indicadores");

		idOpciones.setItems(menu);
		idOpciones.getSelectionModel().select(0);
	}

	public void setProgramaPrincipal(Main ProgramaPrincipal) {
		this.ProgramaPrincipal = ProgramaPrincipal;
	}
	
	public void setStageMenuPrincipal(Stage stagePrincipal) {
		this.stagePrincipalMenuP = stagePrincipal;
	}

}
