package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		Longevidad longevidad = new Longevidad(t);
		if (idAnios.getText().equals("")) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Debe crear agregar la cantidad de a�os", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		Condicion cond = new Filtro(longevidad, Integer.parseInt(idAnios.getText()));
		try {
			meto.agregarCondicion(cond);
		} catch (Exception e) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Debe crear la metodologia antes de cargarle condiciones", "Error",
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

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		super.initialize(arg0, arg1);

	}

}