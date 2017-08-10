package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.event.ActionEvent;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;

import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CondicionSumatoria implements Initializable {
	private Stage stagePrincipal;
	private Traductor t;
	private ProcesarIndicadores procesador1;
	private Metodologia meto;

	@FXML
	private ComboBox<String> idIndicador;
	@FXML
	private TextField idComparador;
	@FXML
	private TextField idValor;
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
	private void comparador(ActionEvent event) {

	};

	@FXML
	private void valor(ActionEvent event) {

	};

	@FXML
	private void fechaInicio(ActionEvent event) {

	};

	@FXML
	private void fechaFin(ActionEvent event) {

	};

	@FXML
	private void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException {
		Sumatoria sum = new Sumatoria(t.buscarIndicador(idIndicador.getValue()), t);
		String fechain[] = idFechaInicio.getText().split("/");
		String fechafin[] = idFechaFin.getText().split("/");
		
		Periodo periodo = new Periodo(
				LocalDate.of(cambiarFechaInt(2, fechain), cambiarFechaInt(1, fechain), cambiarFechaInt(0, fechain)),
				LocalDate.of(cambiarFechaInt(2, fechafin), cambiarFechaInt(1, fechafin), cambiarFechaInt(0, fechafin)));
		
		Condicion condicionSumatoria = new FiltroSegunEcuacion(sum, Integer.parseInt(idValor.getText()),
				idComparador.getText(), periodo);
		meto.agregarCondicion(condicionSumatoria);

		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, "Condicion cargada", "Cargado satisfactoriamente",
				JOptionPane.INFORMATION_MESSAGE);
		idComparador.setText("");
		idValor.setText("");
		idFechaInicio.setText("");
		idFechaFin.setText("");
		

	};

	@FXML
	private void cerrar(ActionEvent event) {
		this.stagePrincipal.close();
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

	}

	public void setMetodologia(Metodologia metod) {
		this.meto = metod;
	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	private int cambiarFechaInt(int posicion, String fecha[]) {
		return Integer.parseInt(fecha[posicion]);
	}

}
