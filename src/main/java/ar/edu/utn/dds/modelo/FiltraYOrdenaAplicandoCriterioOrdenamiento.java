package ar.edu.utn.dds.modelo;


import java.util.Collections;
import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class FiltraYOrdenaAplicandoCriterioOrdenamiento extends Condicion {


	private String criterioOrdenamiento;// mayorAmenor o menorAmayor, SON
										// PALABRAS NO SIMBOLOS,
	// ingreesa en la interfaz grafica

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {

		setFiltro(false);

		List<PuntajeEmpresa> valoresAizq = super.aplicar();
		/*
		 * solo hay lado izquierdo, entonces ordeno la lista que me devuelve
		 * lado izquierdo y ahi estaria la condicion aplicada
		 */

		if (criterioOrdenamiento.equals("mayorAmenor")) {

			/* ordena de mayor a menor */
			Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion())
					.compareTo(new Double(v2.getResultadoDeAplicarCondicion())));
			// ordeno de mayor a menor
			Collections.reverse(valoresAizq);
			return valoresAizq;
		}
		if (criterioOrdenamiento.equals("menorAmayor")) {

			Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion())
					.compareTo(new Double(v2.getResultadoDeAplicarCondicion())));

			return valoresAizq;
		}

		return valoresAizq;

	}

	public FiltraYOrdenaAplicandoCriterioOrdenamiento(ValorCalculable ladoIzq, Periodo periodos, String criterioOrdenamiento) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
		this.criterioOrdenamiento=criterioOrdenamiento;
		
	}

	

}
