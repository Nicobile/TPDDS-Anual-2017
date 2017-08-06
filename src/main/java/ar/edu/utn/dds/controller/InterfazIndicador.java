package ar.edu.utn.dds.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InterfazIndicador  implements Initializable {
	private Traductor t;
	private ProcesarIndicadores procesador1;
	private Stage stagePrincipal;
	
	public void setTraductor(Traductor tradu){
	   this.t = tradu;
	}
	
	public void setProcesador(ProcesarIndicadores procesador){
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
    private Button idCargar;

    
    @FXML
    void cargaArchivo(ActionEvent event) throws FileNotFoundException, IOException {
    	
    
    	this.procesador1.leerExcel(this.getClass().getResource("/"+idRuta.getText()).getFile());
		
		t.getIndicadores().forEach(unIndicador -> idListInd.getItems().add(unIndicador.getNombre()));
    }

    @FXML
    void cargar(ActionEvent event) {
    	Indicador ind1 = new Indicador(idNomInd.getText(),idexpresion.getText());
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

   
   
    @Override
	 public void initialize(URL location,ResourceBundle resource){
    	//if(t.getIndicadores().size()>0){
    	//(t.getIndicadores().forEach(unIndicador -> idListInd.getItems().add(unIndicador.getNombre()));
    	//}
    	//idListInd.getItems().clear();
    }	
}

