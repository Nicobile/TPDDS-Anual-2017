package ar.edu.utn.dds.interfazGrafica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Periodo;
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

public class MenuCuentas {

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
	private Button idObtenerValorCuenta;
	@FXML
	private ComboBox<String> idCombCuenta;
	@FXML
	private TextField idFechaInicio;
	@FXML
	private TextField idFechaFin;
	@FXML
	private TextField idTotal;

	@FXML
	void cargaArchivo(ActionEvent event) throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		if (!archivosCuentas.buscarArchivo(idRuta.getText())) {
			try {
				this.lectorArchivo.leerArchivo(this.getClass().getResource("/" + idRuta.getText()).getFile());
				archivosCuentas.agregarArchivo(idRuta.getText());
				t.getEmpresas().forEach(unaEmpresa -> idEmpresa.getItems().add(unaEmpresa.getNombre()));
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "El archivo ha sido cargado", "OK", JOptionPane.INFORMATION_MESSAGE);

			} catch (NullPointerException e) {
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "No se encuentra el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "El archivo ya fue cargado", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void obtenerValorCuenta(ActionEvent event) {

		String fechain[] = idFechaInicio.getText().split("/");
		String fechafin[] = idFechaFin.getText().split("/");
		Periodo periodo = new Periodo(
				LocalDate.of(cambiarFechaInt(2, fechain), cambiarFechaInt(1, fechain), cambiarFechaInt(0, fechain)),
				LocalDate.of(cambiarFechaInt(2, fechafin), cambiarFechaInt(1, fechafin), cambiarFechaInt(0, fechafin)));
		Empresa e;
		try {
			e = t.obtenerEmpresa(idEmpresa.getValue());

			e.buscarUnaCuentaPorPeriodo(idCombCuenta.getValue(), periodo).forEach(unaCuenta -> {
				idListCta.getItems().add(unaCuenta.getNombre());
				idListCta.getItems().add(String.valueOf(unaCuenta.getValor()));
			});
			idTotal.setText(String.valueOf(e.consultarValorCuenta(idCombCuenta.getValue(), periodo)));
		} catch (NoSeEncuentraLaCuentaException e1) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "La empresa no posee la cuenta requerida", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NoSeEncuentraLaEmpresaException e1) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "La empresa no se encuentra cargada", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NoSeEncuentraLaCuentaEnElPeriodoException e1) {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "La empresa no posee la cuenta requerida en el periodo seleccionado",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	@FXML
	void cerrar(ActionEvent event) {
		stagePrincipalCta.close();
	}

	@FXML
	void ruta(ActionEvent event) {

	}

	@FXML
	void fechaInicio(ActionEvent event) {

	}

	@FXML
	void fechaFin(ActionEvent event) {

	}

	@FXML
	void empresa(ActionEvent event) throws NoSeEncuentraLaEmpresaException {
		List<Cuenta> list = t.obtenerEmpresa(idEmpresa.getValue()).getCuentas();
		List<Cuenta> cuentas = new ArrayList<>(new HashSet<>(list));

		ObservableList<String> nombreCuentas = FXCollections
				.observableArrayList(cuentas.stream().map(unaC -> unaC.getNombre()).collect(Collectors.toList()));
		idCombCuenta.setItems(nombreCuentas);
	}

	@FXML
	void Combcuenta(ActionEvent event) {

	}

	@FXML
	void listCta(ActionEvent event) {

	}

	@FXML
	void total(ActionEvent event) {

	}

	/* JAVA */

	public void initialize(URL url, ResourceBundle rb) throws NoSeEncuentraLaEmpresaException {

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

	private int cambiarFechaInt(int posicion, String fecha[]) {
		return Integer.parseInt(fecha[posicion]);
	}

}
