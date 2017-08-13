package ar.edu.utn.dds.interfazGrafica;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CondicionDecreciente extends TiposDeCondicion {

	@FXML
	private Button idCerrar;

	@FXML
	private ComboBox<String> idIndicador;

	@FXML
	private TextField idAnios;

	@FXML
	private Button idCargar;

	@Override
	public void setT(Traductor tr) {

		super.setT(tr);
		List<String> list = t.getIndicadores().stream().map(unI -> unI.getNombre()).collect(Collectors.toList());
		ObservableList<String> indicador = FXCollections.observableList(list);

		idIndicador.setItems(indicador);

	}

	@FXML
	void cargar(ActionEvent event) {

		int anios = Integer.parseInt(idAnios.getText());

		String indicador = idIndicador.getValue();

		Decreciente decre;
		try {
			decre = new Decreciente(t.buscarIndicador(indicador), t);
			Condicion cond = new Filtro(decre, anios);
			try {
				meto.agregarCondicion(cond);
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
		idAnios.setText("");
	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	@FXML
	void anios(ActionEvent event) {

	}

	@FXML
	void indicador(ActionEvent event) {

	}

}
