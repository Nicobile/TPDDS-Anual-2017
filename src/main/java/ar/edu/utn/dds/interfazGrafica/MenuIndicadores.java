package ar.edu.utn.dds.interfazGrafica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.ErrorFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.YaHayUnIndicadorConEseNombreException;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuIndicadores implements Initializable {

	private Traductor t;
	private ProcesarIndicadores procesador1;
	private Archivos archivosInd;
	private Stage stagePrincipal;
	private Verificador verificador = new Verificador();

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
	private ComboBox<String> idNomIndca;

	@FXML
	private TextField idResult;

	@FXML
	private Button idCalculo;

	@FXML
	private Button idCargar;

	@FXML
	void cargaArchivo(ActionEvent event) throws FileNotFoundException, IOException {
		if (!archivosInd.buscarArchivo(idRuta.getText())) {
			try {
				this.procesador1.leerExcel(this.getClass().getResource("/" + idRuta.getText()).getFile());
				archivosInd.agregarArchivo(idRuta.getText());
				t.getIndicadores().forEach(unIndicador -> {
					idListInd.getItems().add(unIndicador.getNombre());
					idNomIndca.getItems().add(unIndicador.getNombre());
				});
				verificador.mostrarInfo("El archivo se cargo satisfactoriamente", "Informacion");

			} catch (NullPointerException e) {
				verificador.mostrarError("No se encontro el archivo", "Error");
			}

		} else {
			verificador.mostrarError("Ya se ha cargado ese archivo", "Error");
		}
	}

	@FXML
	void cargar(ActionEvent event) {

		try {
			verificador.textFieldVacio(idNomInd);
			verificador.textFieldVacio(idexpresion);
			Indicador ind1 = new Indicador(idNomInd.getText(), idexpresion.getText());
			t.agregarIndicador(ind1);

			EntityManager session = Utilidades.getEntityManager();
			EntityTransaction et = session.getTransaction();
			et.begin();
			session.persist(ind1);
			et.commit();
			Utilidades.closeEntityManager();
			Indicadores.agregarIndicador(ind1);
			idListInd.getItems().add(ind1.getNombre());
			idNomIndca.getItems().add(ind1.getNombre());
			verificador.mostrarInfo("El indicador se cargo satisfactoriamente", "Informacion");
			idNomInd.setText("");
			idexpresion.setText("");
		} catch (YaHayUnIndicadorConEseNombreException e) {
			verificador.mostrarError("Ya existe un indicador con ese nombre", "Error");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Fala completar uno o mas campos", "Error");
		}
	}

	@FXML
	void calcular(ActionEvent event) throws NoSeEncuentraElIndicadorException {
		idResult.clear();
		try {
			verificador.textFieldVacio(idFechaIni);
			verificador.textFieldVacio(idFechaFin);
			verificador.textFieldVacio(idNombreEmpresa);
			verificador.comboBoxVacio(idNomIndca);
			verificador.verificarFecha(idFechaIni.getText());
			verificador.verificarFecha(idFechaFin.getText());
			String fechain[] = idFechaIni.getText().split("/");
			String fechafin[] = idFechaFin.getText().split("/");
			Periodo p = new Periodo(
					LocalDate.of(cambiarFechaInt(2, fechain), cambiarFechaInt(1, fechain), cambiarFechaInt(0, fechain)),
					LocalDate.of(cambiarFechaInt(2, fechafin), cambiarFechaInt(1, fechafin),
							cambiarFechaInt(0, fechafin)));
			idResult.setText(String.valueOf(t.calcular(idNombreEmpresa.getText(), p, idNomIndca.getValue())));

		} catch (NoSeEncuentraLaEmpresaException e) {

			verificador.mostrarError("No se encuentra la empresa", "Error");
		} catch (NoSeEncuentraLaCuentaException c) {
			verificador.mostrarError("La empresa no dispone de la cuenta que requiere el indicador para el calculo",
					"Error");

		} catch (NoSeEncuentraLaCuentaEnElPeriodoException d) {
			verificador.mostrarError(
					"La empresa no dispone de la cuenta en el periodo que requiere el indicador para el calculo",
					"Error");

		} catch (IllegalArgumentException f) {
			verificador.mostrarError("El indicador posee algun error en la expresion", "Error");

		} catch (CampoVacioException e) {
			verificador.mostrarError("Falta completar uno o mas campos", "Error");
		} catch (ErrorFechaException e) {
			verificador.mostrarError("La fecha debe estar en el formato dd/mm/aaaa", "Error");
		}

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
	void nomIndca(ActionEvent event) {
	}

	@FXML
	void result(ActionEvent event) {
	}

	/* JAVA */

	@Override
	public void initialize(URL location, ResourceBundle resource) {

	}

	public void setListaArchivos(Archivos archivos) {
		archivosInd = archivos;

	}

	private int cambiarFechaInt(int posicion, String fecha[]) {
		return Integer.parseInt(fecha[posicion]);
	}

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

}
