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

public class MenuPrincipal implements Initializable {

	private Main ProgramaPrincipal;

	@FXML
	private ComboBox<String> idOpciones;

	@FXML
	private Button idIngresar;

	@FXML
	void ingresar(ActionEvent event) {
		if (idOpciones.getValue().equals("Cargar Empresas y Cuentas")) {
			ProgramaPrincipal.menuCuentas();

		}
		if (idOpciones.getValue().equals("Menu Metodologia")) {
			ProgramaPrincipal.mostrarVentanaPrincipal();

		}
		if (idOpciones.getValue().equals("Menu Indicadores")) {
			ProgramaPrincipal.menuInterfazIndicadores();
		}
	}

	@FXML
	void opciones(ActionEvent event) {

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

}
