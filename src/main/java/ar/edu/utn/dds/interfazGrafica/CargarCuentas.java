package ar.edu.utn.dds.interfazGrafica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CargarCuentas {

	Stage stagePrincipalCta;
	private Traductor t;
	private LectorArchivo lectorArchivo;
	private Archivos archivosCuentas;

	@FXML
	private Button idCerrar;

	@FXML
	private TextField idRuta;

	@FXML
	private Button idCargarArchivo;

	@FXML
	private ListView<String> idListCta;

	@FXML
	private ComboBox<String> idEmpresa;

	@FXML
	private Button idMostrarCta;

	@FXML
	void cargaArchivo(ActionEvent event) throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		if (!archivosCuentas.buscarArchivo(idRuta.getText())) {
			try {
				this.lectorArchivo.leerArchivo(this.getClass().getResource("/" + idRuta.getText()).getFile());
			} catch (NullPointerException e) {
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "No se encuentra el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
			archivosCuentas.agregarArchivo(idRuta.getText());
			t.getEmpresas().forEach(unaEmpresa -> idEmpresa.getItems().add(unaEmpresa.getNombre()));
		} else {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "El archivo ya fue cargado", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void mostrarCta(ActionEvent event) throws NoSeEncuentraLaEmpresaException {
		idListCta.getItems().clear();
		t.obtenerEmpresa(idEmpresa.getValue()).getCuentas()
				.forEach(unaCuenta -> idListCta.getItems().add(unaCuenta.getNombre()));
	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipalCta.close();
	}

	@FXML
	void ruta(ActionEvent event) {

	}

	@FXML
	void empresa(ActionEvent event) {

	}

	@FXML
	void listCta(ActionEvent event) {

	}

	/* JAVA */

	public void initialize(URL url, ResourceBundle rb) {

		ObservableList<String> empresa = FXCollections.observableArrayList();

		idEmpresa.setItems(empresa);
	}

	public void setLectorArchivo(LectorArchivo lector) {
		this.lectorArchivo = lector;
		t.getEmpresas().forEach(unaEmpresa -> idEmpresa.getItems().add(unaEmpresa.getNombre()));

	}

	public void setListaArchivos(Archivos archivos) {
		archivosCuentas = archivos;
	}

	public void setStagePrincipalCuenta(Stage stagePrincipal) {
		this.stagePrincipalCta = stagePrincipal;
	}

	public void setTraductor(Traductor tradu) {
		this.t = tradu;
	}

}
