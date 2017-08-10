package ar.edu.utn.dds.interfazGrafica;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import ar.edu.utn.dds.modelo.Metodologia;

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

public class CondicionSumatoria implements Initializable{
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
	private void cargar(ActionEvent event) {
		
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

}
