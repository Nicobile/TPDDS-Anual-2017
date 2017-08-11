package ar.edu.utn.dds.interfazGrafica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;

import javax.script.ScriptException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;

import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;

public class CondicionDecreciente extends TiposDeCondicion {

	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;

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
		// TODO Auto-generated method stub
		super.setT(tr);
		List<String> list = t.getIndicadores().stream().map(unI -> unI.getNombre()).collect(Collectors.toList());
		ObservableList<String> indicador = FXCollections.observableList(list);

		idIndicador.setItems(indicador);

	}

	@FXML
	void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		int anios = Integer.parseInt(idAnios.getText());

		String indicador = idIndicador.getValue();

		Decreciente decre = new Decreciente(t.buscarIndicador(indicador), t);
		Condicion cond = new Filtro(decre, anios);

		meto.agregarCondicion(cond);
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
