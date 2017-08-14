package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.ResourceBundle;

import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Promedio;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CondicionPromedio extends TiposDeCondicion {

	@FXML
	private ComboBox<String> idIndicador;
	@FXML
	private ComboBox<String> idCriterio;
	@FXML
	private TextField idFechaInicio;
	@FXML
	private TextField idFechaFin;
	@FXML
	private TextField idComparador;
	@FXML
	private TextField idValor;
	@FXML
	private Button idCargar;
	@FXML
	private Button idCerrar;

	@Override
	public void setT(Traductor tr) {

		super.setT(tr);
		idIndicador.setItems(super.indicadoresCargados());
		idCriterio.setItems(super.criteriosOrdenamiento());

	}

	@FXML
	private void indicador(ActionEvent event) {

	};

	@FXML
	private void criterio(ActionEvent event) {

	};

	@FXML
	private void fechaInicio(ActionEvent event) {

	};

	@FXML
	private void fechaFin(ActionEvent event) {

	};

	@FXML
	private void comparador(ActionEvent event) {

	};

	@FXML
	private void valor(ActionEvent event) {

	};

	@FXML
	private void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException {
		try {
			verificador.comboBoxVacio(idIndicador);
			verificador.textFieldVacio(idFechaInicio);
			verificador.textFieldVacio(idFechaFin);
			verificador.textFieldVacio(idValor);
			verificador.textFieldVacio(idComparador);
			verificador.comboBoxVacio(idCriterio);
			Promedio promedio = new Promedio(t.buscarIndicador(idIndicador.getValue()), t);
			String fechain[] = idFechaInicio.getText().split("/");
			String fechafin[] = idFechaFin.getText().split("/");
			Periodo periodo = super.armarPeriodo(fechain, fechafin);
			super.armarDobleCondicion(promedio, idValor.getText(), idComparador.getText(), periodo,
					idCriterio.getValue());
			verificador.mostrarInfo("Condicion cargada", "Informacion");
			idComparador.setText("");
			idValor.setText("");
			idFechaInicio.setText("");
			idFechaFin.setText("");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto completar uno o mas campos", "Error");
		}

	};

	@FXML
	private void cerrar(ActionEvent event) {
		this.stagePrincipal.close();
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
