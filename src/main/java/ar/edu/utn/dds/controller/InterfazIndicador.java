package ar.edu.utn.dds.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.interfazGrafica.Archivos;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InterfazIndicador implements Initializable {
	private Traductor t;
	private ProcesarIndicadores procesador1;
	private Archivos archivosInd;
	private Stage stagePrincipal;

	public void setTraductor(Traductor tradu) {
		this.t = tradu;
	}

	public void setProcesador(ProcesarIndicadores procesador) {
		this.procesador1 = procesador;
		t.getIndicadores().forEach(unIndicador -> idListInd.getItems().add(unIndicador.getNombre()));
	}

	public void setStagePrincipalInd(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	@FXML
	private Button idCerrar;

	@FXML
	private TextField idRuta;

	@FXML
	private Button idCargarArchivo;

	@FXML
	private TextField idNomInd;

	@FXML
	private ListView<String> idListInd;

	@FXML
	private TextField idexpresion;
	
	@FXML
	private TextField idNombreEmpresa;
	
	@FXML
	private TextField idFechaIni;
	
	@FXML
	private TextField idFechaFin;
	
	@FXML
	private TextField idNomIndca;
	
	@FXML
	private TextField idResult;
	
	@FXML
	private Button idCalculo;

	@FXML
	private Button idCargar;

	@FXML
	void cargaArchivo(ActionEvent event) throws FileNotFoundException, IOException {
		if (!archivosInd.buscarArchivo(idRuta.getText())) {
			this.procesador1.leerExcel(this.getClass().getResource("/" + idRuta.getText()).getFile());
			archivosInd.agregarArchivo(idRuta.getText());
			t.getIndicadores().forEach(unIndicador -> idListInd.getItems().add(unIndicador.getNombre()));

		}
		else{
		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, "El archivo ya fue cargado", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void cargar(ActionEvent event) {
		Indicador ind1 = new Indicador(idNomInd.getText(), idexpresion.getText());
		t.agregarIndicador(ind1);
		idListInd.getItems().add(ind1.getNombre());
	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipal.close();
	}

	@FXML
	void expresion(ActionEvent event) {

	}

	@FXML
	void listInd(ActionEvent event) {

	}

	@FXML
	void nomInd(ActionEvent event) {

	}

	@FXML
	void ruta(ActionEvent event) {

	}
	
	@FXML
	void nomEmp(ActionEvent event) {

	}
	
	@FXML
	void fechaini(ActionEvent event) {

	}	

	@FXML
	void fechafin(ActionEvent event) {

	}
		
	@FXML
	void idNomIndca(ActionEvent event) {

	}
	
	@FXML
	void result(ActionEvent event){

	}
	
	@FXML
	void calcular(ActionEvent event) throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException{
	  String fechain[] = idFechaIni.getText().split("/");
	  String fechafin[] = idFechaFin.getText().split("/");
      Periodo p = new Periodo(LocalDate.of(cambiarFechaInt(2,fechain),cambiarFechaInt(1,fechain), cambiarFechaInt(0,fechain)),LocalDate.of(cambiarFechaInt(2,fechafin),cambiarFechaInt(1,fechafin), cambiarFechaInt(0,fechafin)) );
      idResult.setText(String.valueOf(t.calcular(idNombreEmpresa.getText(), p, idNomInd.getText()))); 
	}
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		// if(t.getIndicadores().size()>0){
		// (t.getIndicadores().forEach(unIndicador ->
		// idListInd.getItems().add(unIndicador.getNombre()));
		// }
		// idListInd.getItems().clear();
	}

	public void setListaArchivos(Archivos archivos) {
		archivosInd = archivos;

	}
	
	private int cambiarFechaInt(int posicion, String fecha[]){
		return Integer.parseInt(fecha[posicion]);
	}

}
