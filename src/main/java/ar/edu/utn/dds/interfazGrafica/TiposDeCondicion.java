package ar.edu.utn.dds.interfazGrafica;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class TiposDeCondicion implements Initializable {
	public Stage stagePrincipal;
	public Traductor t=new Traductor();
	public Metodologia meto;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	

	}

	public void setMetodologia(Metodologia metod) {
		this.meto = metod;
	}

	public void setT(Traductor tr)  {	
		this.t=tr;
		System.out.println(t.getIndicadores().size());
	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	public Traductor getT() {
		return t;
	}

	public Periodo armarPeriodo(String fechaInicio[], String fechaFin[]) {

		Periodo periodo = new Periodo(
				LocalDate.of(cambiarFechaInt(2, fechaInicio), cambiarFechaInt(1, fechaInicio),
						cambiarFechaInt(0, fechaInicio)),
				LocalDate.of(cambiarFechaInt(2, fechaFin), cambiarFechaInt(1, fechaFin), cambiarFechaInt(0, fechaFin)));
		return periodo;

	}

	private int cambiarFechaInt(int posicion, String fecha[]) {
		return Integer.parseInt(fecha[posicion]);
	}

}
