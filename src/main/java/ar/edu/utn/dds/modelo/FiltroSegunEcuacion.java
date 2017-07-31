package ar.edu.utn.dds.modelo;

import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class FiltroSegunEcuacion extends Condicion {

	private Double ladoDer;/*
							 * lado der SIEMPRE va a ser un numero PUEDE
							 */

	

	public FiltroSegunEcuacion(ValorCalculable ladoIzq, Double ladoDer, String comparador, Periodo periodos) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
		this.ladoDer = ladoDer;
		super.setComparador(comparador);

	}

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {

		/*
		 * en este caso tengo que filtrar aquellos que cumplan con la condicion
		 * con lo cual tengo que armar la condicion
		 */
		List<PuntajeEmpresa> valoresAizq = super.aplicar();
		setFiltro(true);

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine interprete = manager
				.getEngineByName("js"); /* con esto armo la ecuacion */
		/* y el lado derecho siempre van a hacer los mismos de la formula */
		String ladoder = String.valueOf(ladoDer);

		List<PuntajeEmpresa> empresasQueCumplenCond = valoresAizq.stream().filter(unV -> {
			try {
				return (boolean) interprete
						.eval((String.valueOf(unV.getResultadoDeAplicarCondicion())) + super.getComparador() + ladoder);
			} catch (ScriptException e) {

				e.printStackTrace();
			}
			return false;
		}).collect(Collectors.toList());

		return empresasQueCumplenCond;

	}

}
