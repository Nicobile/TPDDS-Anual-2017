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
import ar.edu.utn.dds.modelo.Metodologia;

import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CondicionDecreciente implements Initializable {

	private Stage stagePrincipal;
	private Traductor t;
	private Metodologia meto;
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

	/* JAVA */

	public void initialize(URL url, ResourceBundle rb) {

		/* inicializar los recursos necesarios */
		// habria que cambiar esto de ller los archivos habria que ir a la lista de
		// archivos que tenemos en archivos y ver si son de indicadores o datos y
		// trabajar con eso

		this.t = new Traductor();
		this.lector = new LectorArchivo(t);

		try {
			this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		} catch (IOException | NoSeEncuentraLaEmpresaException e) {
			e.printStackTrace();
		}

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

	public void setStagePrincipal3(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}
}
