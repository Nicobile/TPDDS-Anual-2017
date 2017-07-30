package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Promedio extends ValorCalculable {

	public Promedio(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);

		ArrayList<PuntajeEmpresa> sumatoria = super.sumatoriaIndicadores(listaEmpresas, periodos);
		int cantidadDeAnios = periodos.getFechaFin().getYear() - periodos.getFechaInicio().getYear();

		for (int i = 0; (i < sumatoria.size()); i++) {
			sumatoria.get(i).setResultadoDeAplicarCondicion(
					sumatoria.get(i).getResultadoDeAplicarCondicion() / cantidadDeAnios);
		}
		return sumatoria;

	}

}
