package ar.edu.utn.dds.modelo;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

@Entity  
@DiscriminatorValue("condicionFiltroConEcuacion") 

public class FiltroSegunEcuacion extends Condicion {

	/* lado der SIEMPRE va a ser un numero PUEDE */
	private int ladoDer;
	private String comparador;
	
	public FiltroSegunEcuacion() {
		
	}

	public FiltroSegunEcuacion(ValorCalculable ladoIzq, int ladoDer, String comparador, Periodo periodos) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
		this.ladoDer = ladoDer;
		this.comparador = comparador;

	}

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {

		/*
		 * Tengo que filtrar aquellos que cumplan con la condicion con lo cual
		 * tengo que armar la condicion
		 */

		List<PuntajeEmpresa> valoresAizq = super.aplicar();
		setFiltro(true);

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine interprete = manager.getEngineByName("js");

		/*
		 * Armo la ecuacion y el lado der siempre van a hacer los mismos de la
		 * formula
		 */

		String ladoder = String.valueOf(ladoDer);

		List<PuntajeEmpresa> empresasQueCumplenCond = valoresAizq.stream().filter(unV -> {

			try {
				return (boolean) interprete
						.eval((String.valueOf(unV.getResultadoDeAplicarCondicion())) + comparador + ladoder);
			} catch (ScriptException e) {

				e.printStackTrace();
			}
			return false;

		}).collect(Collectors.toList());

		return empresasQueCumplenCond;
	}
}
