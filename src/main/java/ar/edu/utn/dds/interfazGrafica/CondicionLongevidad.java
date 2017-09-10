package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.ResourceBundle;

import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Longevidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CondicionLongevidad extends TiposDeCondicion {

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

		try {
			verificador.textFieldVacio(idAnios);
			Longevidad longevidad = new Longevidad(t);
			Condicion cond = new Filtro(longevidad, Integer.parseInt(idAnios.getText()));
			meto.agregarCondicion(cond);
			super.persistirCrecienteoDecrecienteoLongevidad(longevidad, cond);
			verificador.mostrarInfo("Condicion cargada satisfactoriamente", "Informacion");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto ingresar una cantidad de a�os", "Error");
		}
		idAnios.setText("");
	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		super.initialize(arg0, arg1);

	}

}
