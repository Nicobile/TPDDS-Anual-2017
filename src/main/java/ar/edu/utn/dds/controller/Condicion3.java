package ar.edu.utn.dds.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Condicion3 implements Initializable {

	private Stage stagePrincipal;
	private Traductor t;
	private Metodologia meto;
	private String nombre;
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;

	public void setNombreMetod(String nombre) {
		this.nombre = nombre;
	}

	public void setStagePrincipal3(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	@FXML
	private Button idCerrar;

	@FXML
	private ComboBox<String> idLadoIzq;

	@FXML
	private ComboBox<String> idIndicador;

	@FXML
	private ListView<String> idLista;

	@FXML
	private TextField idPeriodoIni;

	@FXML
	private TextField idPeriodoFin;

	@FXML
	private Button idCargar;

	@FXML
	void periodoFin(ActionEvent event) {

	}

	@FXML
	void periodoIni(ActionEvent event) {

	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	@FXML
	void ladoIzq(ActionEvent event) {

	}

	@FXML
	void indicador(ActionEvent event) {

	}

<<<<<<< HEAD
	 }
	 
	 @FXML
	 void lista(ActionEvent event) {
		 idLista.getItems().clear();
	 }
=======
	@FXML
	void lista(ActionEvent event) {

	}
>>>>>>> 98369f843642c9e5be7c54f9b2ba3de0cf67845d

	@FXML
	void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

<<<<<<< HEAD
    @FXML
    void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
    	
    	String periodoIni = (idPeriodoIni.getText());
    	String periodoFin = (idPeriodoFin.getText());
    	Periodo periodo = new Periodo(periodoIni,periodoFin);

    	String indicador = idIndicador.getValue();
    	idLista.getItems().clear();
    	

    	
    	if (idLadoIzq.getValue().equals("Longevidad")){
    		Longevidad longevidad = new Longevidad(t);

    		Condicion cond = new Filtro(longevidad, periodo, 20);
    		meto.agregarCondicion(cond);
    
    	}
    	if (idLadoIzq.getValue().equals("Creciente")){
    		Creciente creciente = new Creciente(t.buscarIndicador(indicador), t);

    		Condicion cond = new Filtro(creciente, periodo, 4);
    		meto.agregarCondicion(cond);

    	}
    	if (idLadoIzq.getValue().equals("Decreciente")){
    		Decreciente decre = new Decreciente(t.buscarIndicador(indicador), t);
        	Condicion cond = new Filtro(decre, periodo, 4);
        	meto.agregarCondicion(cond);
    	}
    	
    	ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
    	
    	for (int j = 0; j < empresas.size(); j++){
    		idLista.getItems().addAll(empresas.get(j).getNombreEmpresa());
    	}
    	
    	if (empresas.size()>0){
    	//	empresas.clear();
    		
    	}
    		
   
    }

	 
    public void initialize(URL url, ResourceBundle rb) {
	       // TODO
    	//inicializar los recursos necesarios 
    	this.t = new Traductor();
    	this.lector = new LectorArchivo(t);
    	try {
			this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		} catch (IOException | NoSeEncuentraLaEmpresaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.procesador1 = new ProcesarIndicadores(t);
		try {
			this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.meto = new Metodologia("metod");
    	ObservableList<String> ladoIzq = FXCollections.observableArrayList("Longevidad","Creciente","Decreciente");
		
=======
		// inicializar los recursos necesarios
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		this.meto = new Metodologia("metod");

		// esto lo hago pq pablo me recomendo que le cambie el tipo de dato del
		// constructor de periodo
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate fechaI = LocalDate.parse(idPeriodoIni.getText(), formatter);
		LocalDate fechaF = LocalDate.parse(idPeriodoFin.getText(), formatter);

		Periodo periodo = new Periodo(fechaI, fechaF);

		String indicador = idIndicador.getValue();

		if (idLadoIzq.getValue().equals("Longevidad")) {
			Longevidad longevidad = new Longevidad(t);

			Condicion cond = new Filtro(longevidad, periodo, 20);
			meto.agregarCondicion(cond);

		}
		if (idLadoIzq.getValue().equals("Creciente")) {
			Creciente creciente = new Creciente(t.buscarIndicador(indicador), t);

			Condicion cond = new Filtro(creciente, periodo, 4);
			meto.agregarCondicion(cond);

		}
		if (idLadoIzq.getValue().equals("Decreciente")) {
			Decreciente decre = new Decreciente(t.buscarIndicador(indicador), t);
			Condicion cond = new Filtro(decre, periodo, 4);
			meto.agregarCondicion(cond);
		}

		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();

		for (int j = 0; j < empresas.size(); j++) {
			idLista.getItems().addAll(empresas.get(j).getNombreEmpresa());
		}

	}

	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		ObservableList<String> ladoIzq = FXCollections.observableArrayList("Longevidad", "Creciente", "Decreciente");

>>>>>>> 98369f843642c9e5be7c54f9b2ba3de0cf67845d
		idLadoIzq.setItems(ladoIzq);
		idLadoIzq.getSelectionModel().select(0);

		ObservableList<String> indicador = FXCollections.observableArrayList("i_ROE", "i_NivelDeuda", "i_MargenVentas",
				"i_IndicadorD", "i_IngresoNeto", "i_Solvencia", "j_IndicadorF");
		idIndicador.setItems(indicador);

	}
}
