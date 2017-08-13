package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.script.ScriptException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoHayCondicionesException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainGraf implements Initializable {

	private Main ProgramaPrincipal;

	private Traductor t;
	private String texto;
	private Metodologia metod; // primero cargar la metodologia

	@FXML
	private ComboBox<String> idMetodologia;

	@FXML
	private Button idBtnCerrar;

	@FXML
	private Button idBtnSeleccionar;

	@FXML
	private Button idAplicar;

	@FXML
	private ListView<String> idEmpresas;

	@FXML
	private ComboBox<String> idBtnCondicion;

	@FXML
	private TextField idCond;

	@FXML
	private TextField idTextMetod;

	@FXML
	private Button idCargar;

	@FXML
	void cargar(ActionEvent event) {
		try {
			metod = new Metodologia(idTextMetod.getText());
			t.agregarMetodologia(metod);
			idMetodologia.getItems().add(metod.getNombre());
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Metodologia creada exitosamente", "Informacion",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (MetodologiaYaExisteException e) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Esa metodologia ya existe", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void btnSeleccionar(ActionEvent event) {
		ProgramaPrincipal.mostrarCondicion(t.buscarMetodologia(idMetodologia.getValue()), idBtnCondicion.getValue());

	}

	@FXML
	private void aplicar(ActionEvent event) {
		idEmpresas.getItems().clear();
		List<PuntajeEmpresa> empresasQueCumplen;

		try {
			empresasQueCumplen = t.buscarMetodologia(idMetodologia.getValue()).aplicarMetodologia();
			empresasQueCumplen.forEach(empresa -> idEmpresas.getItems().add(empresa.getNombreEmpresa()));
		} catch (NoHayEmpresasQueCumplanLaCondicionException e) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "No se encontraron empresas que cumplan con la metodologia",
					"Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (NoSeEncuentraLaEmpresaException e) {

		} catch (ScriptException e) {

		} catch (NoSePudoOrdenarLaCondicionException e) {

		} catch (NoSeEncuentraLaCuentaException e) {

		} catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {

		} catch (NoSeEncuentraElIndicadorException e) {

		} catch (NoHayCondicionesException e) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Esa metodologia no contiene condiciones", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@FXML
	void btnCerrar(ActionEvent event) {

	}

	@FXML
	void condiciones(ActionEvent event) {

	}

	@FXML
	void btnCondicion(ActionEvent event) {
	}

	@FXML
	void comboMetod(ActionEvent event) {
		idCond.clear();
		idCond.setText(
				String.valueOf(t.buscarMetodologia(idMetodologia.getValue()).getCondicionesDeMetodologia().size()));
	}

	@FXML
	void textMetod(ActionEvent event) {
	}

	/* JAVA */

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		ObservableList<String> condiciones = FXCollections.observableArrayList("Longevidad", "Creciente", "Decreciente",
				"Mediana", "Promedio", "Sumatoria");

		idBtnCondicion.setItems(condiciones);
		idBtnCondicion.getSelectionModel().select(0);

	}

	public void setProgramaPrincipal(Main ProgramaPrincipal) {
		this.ProgramaPrincipal = ProgramaPrincipal;
	}

	public void setTraductor(Traductor tradu) {
		this.t = tradu;
		t.getMetodologias().forEach(unaMetodologia -> idMetodologia.getItems().add(unaMetodologia.getNombre()));
	}

	public String getNombre() {
		return this.texto;
	}
}
