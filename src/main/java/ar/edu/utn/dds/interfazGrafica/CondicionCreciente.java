package ar.edu.utn.dds.interfazGrafica;

import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CondicionCreciente extends TiposDeCondicion {

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
		// TODO Auto-generated method stub
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
			Creciente cre = new Creciente(t.buscarIndicador(indicador), t);
			Condicion cond = new Filtro(cre, anios);
			meto.agregarCondicion(cond);
			verificador.mostrarInfo("Condicion cargada satisfactoriamente", "Informacion");
		} catch (CampoVacioException e) {
			verificador.mostrarError("Falto seleccionar un indicador o ingresar una cantidad de a�os", "Error");
		}

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