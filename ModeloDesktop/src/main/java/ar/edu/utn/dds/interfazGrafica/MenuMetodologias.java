package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;
import javax.script.ScriptException;

import ar.edu.utn.dds.entidades.Metodologias;
import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoHayCondicionesException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuMetodologias implements Initializable {

	Stage stagePrincipalMeto;
	private Traductor t;
	private String texto;
	private Metodologia metod; // primero cargar la metodologia
	Verificador verificador = new Verificador();
	private List<Empresa> empresas = new ArrayList<>();

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
	private TextField idTextMetod;

	@FXML
	private Button idCargar;
	@FXML
	private ComboBox<String> idComboEmpresas;
	@FXML
	private Button idEliminarEmpresas;
	@FXML
	private Button idCargarEmpresas;

	@FXML
	void cargar(ActionEvent event) {
		try {

			verificador.textFieldVacio(idTextMetod);

			metod = new Metodologia(idTextMetod.getText());

			t.agregarMetodologia(metod);

			idMetodologia.getItems().add(metod.getNombre());

			Metodologias.persistirMetodologia(metod);

			verificador.mostrarInfo("Metodologia creada exitosamente", "Informacion");

			idTextMetod.setText("");

		} catch (MetodologiaYaExisteException e) {
			verificador.mostrarError("Ya existe una metodologia con ese nombre", "Error");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto completar el campo nombre de la metodologia", "Error");
		} catch (PersistenceException e) {
			verificador.mostrarError("Ya Existe una metodologia con ese nombre", "Error");
		}
	}

	@FXML
	private void btnSeleccionar(ActionEvent event) {
		try {

			verificador.comboBoxVacio(idMetodologia);

			mostrarCondicion(t.buscarMetodologia(idMetodologia.getValue()), idBtnCondicion.getValue());

		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto seleccionar la metodologia a la cual se le cargara la condicion", "Error");
		}

	}

	@FXML
	private void aplicar(ActionEvent event) throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		idEmpresas.getItems().clear();
		List<PuntajeEmpresa> empresasQueCumplen;

		try {
			verificador.comboBoxVacio(idMetodologia);
			Metodologia metodologia = t.buscarMetodologia(idMetodologia.getValue());
			metodologia.setContador(0);
			empresasQueCumplen = metodologia.aplicarMetodologia(empresas);

			empresasQueCumplen.forEach(empresa -> idEmpresas.getItems().add(empresa.getNombreEmpresa()));

		} catch (NoHayEmpresasQueCumplanLaCondicionException e) {
			verificador.mostrarInfo("No se encontraron empresas que cumplan con la metodologia", "Informacion");
		} catch (NoHayCondicionesException e) {
			verificador.mostrarError("La metodologia seleccionada no posee condiciones", "Error");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto seleccionar la metodologia a la cual se le cargara la condicion", "Error");
		}

	}

	public void mostrarCondicion(Metodologia metod, String condicion) {

		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Condicion" + condicion + ".fxml"));

			BorderPane ventana;
			ventana = (BorderPane) loader.load();
			TiposDeCondicion controller = loader.getController();
			controller.setMetodologia(metod);
			controller.setT(t);

			Stage stage = new Stage();
			stage.setTitle("Condicion" + condicion);
			stage.initOwner(stagePrincipalMeto);
			Scene scene = new Scene(ventana);
			stage.setScene(scene);

			controller.setStagePrincipal(stage);

			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void btnCerrar(ActionEvent event) {
		stagePrincipalMeto.close();
	}

	@FXML
	void btnCondicion(ActionEvent event) {
	}

	@FXML
	void comboMetod(ActionEvent event) {

	}

	@FXML
	void textMetod(ActionEvent event) {
	}

	@FXML
	void comboEmpresas(ActionEvent event) {
	}

	@FXML
	void eliminarEmpresas(ActionEvent event) {
		empresas.clear();
		verificador.mostrarInfo("La lista de empresas, ha sido vaciada", "Informacion");

	}

	@FXML
	void cargarEmpresas(ActionEvent event) throws NoSeEncuentraLaEmpresaException {

		empresas.add(t.obtenerEmpresa(idComboEmpresas.getValue()));
		verificador.mostrarInfo("La empresa seleccionada ha sido agregada a la lista de evaluacion", "Informacion");

	}
	/* JAVA */

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		ObservableList<String> condiciones = FXCollections.observableArrayList("Longevidad", "Creciente", "Decreciente",
				"Mediana", "Promedio", "Sumatoria");

		idBtnCondicion.setItems(condiciones);

		idBtnCondicion.getSelectionModel().select(0);

	}

	public void setTraductor(Traductor tradu) {
		this.t = tradu;
		t.getMetodologias().forEach(unaMetodologia -> idMetodologia.getItems().add(unaMetodologia.getNombre()));

		List<Metodologia> metodologiasPersistidos = Metodologias.setMetodologias();

		// para que ande una metodologia persistida
		metodologiasPersistidos.forEach(unaMeto -> {
			idMetodologia.getItems().add(unaMeto.getNombre());
			if (!t.getMetodologias().contains(unaMeto)) {
				t.agregarMetodologia(unaMeto);
			}
			unaMeto.getCondicionesDeMetodologia().stream().forEach(unC -> {
				unC.getLadoIzq().setTraductor(t);
			});
		});
		ObservableList<String> empre = FXCollections
				.observableList(t.getEmpresas().stream().map(unaE -> unaE.getNombre()).collect(Collectors.toList()));
		idComboEmpresas.setItems(empre);

	}

	public String getNombre() {
		return this.texto;
	}

	public void setStagePrincipalMeto(Stage stagePrincipal) {
		this.stagePrincipalMeto = stagePrincipal;
	}

}
