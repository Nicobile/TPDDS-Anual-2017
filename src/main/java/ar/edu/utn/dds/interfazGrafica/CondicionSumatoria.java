package ar.edu.utn.dds.interfazGrafica;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CondicionSumatoria extends TiposDeCondicion {

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
	private void cargar(ActionEvent event) {
		Sumatoria sum;
		try {
			sum = new Sumatoria(t.buscarIndicador(idIndicador.getValue()), t);

			String fechain[] = idFechaInicio.getText().split("/");
			String fechafin[] = idFechaFin.getText().split("/");
			Periodo periodo = super.armarPeriodo(fechain, fechafin);

			Condicion condicionSumatoria = new FiltroSegunEcuacion(sum, Integer.parseInt(idValor.getText()),
					idComparador.getText(), periodo);
			try {
				meto.agregarCondicion(condicionSumatoria);
			} catch (Exception e) {
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Debe crear la metodologia antes de cargarle condiciones", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NoSeEncuentraElIndicadorException e) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "No se encuentra el indicador especificado", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

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
		super.stagePrincipal.close();
	};

	@Override
	public void setT(Traductor tr) {

		super.setT(tr);
		List<String> list = super.t.getIndicadores().stream().map(unI -> unI.getNombre()).collect(Collectors.toList());
		ObservableList<String> indicador = FXCollections.observableList(list);

		idIndicador.setItems(indicador);

	}

}
