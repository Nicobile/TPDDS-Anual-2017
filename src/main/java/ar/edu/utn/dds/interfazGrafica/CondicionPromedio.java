package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.event.ActionEvent;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;

import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Promedio;

import ar.edu.utn.dds.modelo.Traductor;

import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;

public class CondicionPromedio extends TiposDeCondicion {

	private ProcesarIndicadores procesador1;

	@FXML
	private ComboBox<String> idIndicador;
	@FXML
	private ComboBox<String> idCriterio;
	@FXML
	private TextField idFechaInicio;
	@FXML
	private TextField idFechaFin;
	@FXML
	private Button idCargar;
	@FXML
	private Button idCerrar;

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
	private void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException {
		Promedio promedio = new Promedio(t.buscarIndicador(idIndicador.getValue()), t);
		String fechain[] = idFechaInicio.getText().split("/");
		String fechafin[] = idFechaFin.getText().split("/");

		Periodo periodo = super.armarPeriodo(fechain, fechafin);

		Condicion condicionSumatoria = new OrdenaAplicandoCriterioOrdenamiento(promedio, periodo,
				idCriterio.getValue());

		meto.agregarCondicion(condicionSumatoria);

		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, "Condicion cargada", "Cargado satisfactoriamente",
				JOptionPane.INFORMATION_MESSAGE);

		idFechaInicio.setText("");
		idFechaFin.setText("");

	};

	@FXML
	private void cerrar(ActionEvent event) {
		this.stagePrincipal.close();
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);

		this.t = new Traductor();

		this.procesador1 = new ProcesarIndicadores(t);

		try {
			this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> list = t.getIndicadores().stream().map(unI -> unI.getNombre()).collect(Collectors.toList());
		ObservableList<String> indicador = FXCollections.observableList(list);

		idIndicador.setItems(indicador);

		List<String> criteriosOrdenamiento = new ArrayList<String>();
		criteriosOrdenamiento.add("mayorAmenor");
		criteriosOrdenamiento.add("menorAmayor");
		ObservableList<String> lista = FXCollections.observableList(criteriosOrdenamiento);

		idCriterio.setItems(lista);

	}

}
