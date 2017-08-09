package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Mediana extends ValorCalculable {

	public Mediana(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);

		for (int j = 0; j < getEmpresas().size(); j++) {
			Empresa e = getEmpresas().get(j);
			List<Cuenta> cuentas = e.filtraCuentasEnPeriodo(periodos);

			/* de las cuentas me quedo con los periodos */

			List<Periodo> periodo = cuentas.stream().map(unaC -> unaC.getPeriodo()).collect(Collectors.toList());

			/* elimino periodos iguales */

			List<Periodo> listaPeriodos = new ArrayList<>(new HashSet<>(periodo));

			/*
			 * ordeno los periodos de menor a mayor, de esta manera se cual periodo es
			 * anterior, y calculo su valor
			 */

			Collections.sort(listaPeriodos, (p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));
			Double[] array = new Double[listaPeriodos.size()];

			/*
			 * es un array de la cantidad de periodos que hay entre en periodo ingresado por
			 * paramentro
			 */

			listaPeriodos.stream().forEach(unP -> {
				for (int i = 0; i < listaPeriodos.size(); i++) {
					try {
						array[i] = getTraductor().calcular(e.getNombre(), unP, getIndicador().getNombre());
					} catch (NoSeEncuentraLaEmpresaException | NoSeEncuentraLaCuentaException
							| NoSeEncuentraLaCuentaEnElPeriodoException | NoSeEncuentraElIndicadorException x) {

					}
					/*
					 * calculo el indicador para una empresa y guardo el resultado en un array para
					 * desps obtener la mediana
					 */
				}
			});

			int empresa = j;
			double mediana;
			Arrays.sort(array);

			if (array.length % 2 == 0) {
				mediana = (array[array.length / 2] + array[array.length / 2 - 1]) / 2;
			} else {
				mediana = array[array.length / 2];
			}

			listaEmpresas.stream()
					.filter(unaE -> unaE.getNombreEmpresa().equals(getEmpresas().get(empresa).getNombre())).findFirst()
					.get().setResultadoDeAplicarCondicion(mediana);
		}

		return listaEmpresas;
	}
}
