package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CondicionLongevidad implements Initializable {

	private Stage stagePrincipal;
	private Metodologia metodologia;
	private Traductor t;

	public void setTraductor(Traductor t) {
		this.t = t;
	}

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
		Condicion cond = new Filtro(longevidad, Integer.parseInt(idAnios.getText()));
		metodologia.agregarCondicion(cond);
		//habria que tirar msje error si no esta creada la metodologia
		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, "Condicion cargada", "Cargado satisfactoriamente",
				JOptionPane.INFORMATION_MESSAGE);
		idAnios.setText("");
	}

	@FXML	
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	public void setMetodologia(Metodologia metod) {
		metodologia = metod;

	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
