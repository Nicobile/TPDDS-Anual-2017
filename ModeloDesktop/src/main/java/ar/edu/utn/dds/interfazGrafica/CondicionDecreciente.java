package ar.edu.utn.dds.interfazGrafica;

import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CondicionDecreciente extends TiposDeCondicion {

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

		super.setT(tr);

		idIndicador.setItems(super.indicadoresCargados());

	}

	@FXML
	void cargar(ActionEvent event) throws NoSeEncuentraElIndicadorException {

		try {
			verificador.comboBoxVacio(idIndicador);
			verificador.textFieldVacio(idAnios);
			int anios = Integer.parseInt(idAnios.getText());

			String indicador = idIndicador.getValue();

			Decreciente decre;
			decre = new Decreciente(Indicadores.getIndicadores().stream().filter(unI->
			unI.getNombre().equals(indicador)).findFirst().get(), t);
			Condicion cond = new Filtro(decre, anios);
			meto.agregarCondicion(cond);
			super.persistirCrecienteoDecrecienteoLongevidad(decre, cond);
			verificador.mostrarInfo("Condicion cargada satisfactoriamente", "Informacion");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto seleccionar un indicador o ingresar una cantidad de a�os", "Error");
		}

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
