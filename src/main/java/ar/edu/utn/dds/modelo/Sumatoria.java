package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Sumatoria extends ValorCalculable {

	public Sumatoria(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		
		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);
		/*
		 * deberia filtrar la lista de cuentas de le empresa durante los ultimos
		 * periodos aplico el indicador a cada uno de los elementos de la lista
		 * firltrada y ordeno por
		 */

		// return super.sumatoriaIndicadores(listaEmpresas, periodos);
		ArrayList<Double> lista = new ArrayList<Double>();
		lista = this.getTraductor().calcularAListaDeEmpresas(getEmpresas(), periodos, getIndicador());

		for (int i = 0; (i < listaEmpresas.size()) && (i < lista.size()); i++) {
			listaEmpresas.get(i).setResultadoDeAplicarCondicion(lista.get(i));
		}
		
		return listaEmpresas;
	}
}