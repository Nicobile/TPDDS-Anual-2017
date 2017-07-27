package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.EntradaDeDatosErroneaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class Condicion {

	private ValorCalculable ladoIzq;
	private Double ladoDer = null;/*
									 * lado der SIEMPRE va a ser un numero PUEDE
									 * * NO INGRESARSE
									 */
	private String comparador = null;/* PUEDE NO INGRESARSE */
	private int periodos;
	private String criterio = null;// mayor o menor, SON PALABRAS NO SIMBOLOS,
									// ingreesa en la interfaz grafica
	private Boolean filtro;
	/*
	 * 3 formas de ingresar por interfaz lado izquierdo derecho comparador y
	 * periodos lado izquierdo periodos y criterio lado izquierdo y periodos
	 */

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
			NoSeEncuentraElIndicadorException {
		/*
		 * ordena a las empresas segun cumplan la condicion
		 */ setFiltro(false);

		ArrayList<PuntajeEmpresa> valoresAizq = new ArrayList<PuntajeEmpresa>();

		valoresAizq = ladoIzq.calcularValor(
				periodos);/*
							 * esto va a aplicar a todas las empresas el lado
							 * izq y los guarda en la lista SIN ORDENAR
							 */
		if ((ladoDer == null && comparador != null) || (comparador == null && ladoDer != null)) {
			throw new EntradaDeDatosErroneaException("Se ingresaron mal los datos para armar la condicion");
		}
		if (ladoDer == null && comparador == null) {
			/*
			 * solo hay lado izquierdo, entonces ordeno la lista que me devuelve
			 * lado izquierdo y ahi estaria la condicion aplicada
			 */
			if (criterio != null) {

				if (criterio.equals("mayor")) {

					/* ordena de mayor a menor */
					Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion())
							.compareTo(new Double(v2.getResultadoDeAplicarCondicion())));
					// ordeno de mayor a menor
					Collections.reverse(valoresAizq);
					return valoresAizq;
				}
				if (criterio.equals("menor")) {

					Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion())
							.compareTo(new Double(v2.getResultadoDeAplicarCondicion())));

					return valoresAizq;
				}
			}

			return valoresAizq;

		}

		else {
			/*
			 * en este caso tengo que filtrar aquellos que cumplan con la
			 * condicion con lo cual tengo que armar la condicion
			 */
			setFiltro(true);

			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine interprete = manager
					.getEngineByName("js"); /* con esto armo la ecuacion */
			/* y el lado derecho siempre van a hacer los mismos de la formula */
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

	public Condicion(ValorCalculable ladoIzq, Double ladoDer, String comparador, int periodos) {
		super();
		this.ladoIzq = ladoIzq;
		this.ladoDer = ladoDer;
		this.comparador = comparador;
		this.periodos = periodos;
	};

	public Condicion(ValorCalculable ladoIzq, int periodos, String criterio) {
		super();
		this.ladoIzq = ladoIzq;
		this.periodos = periodos;
		this.criterio = criterio;
	}

	public Condicion(ValorCalculable ladoIzq, int periodos) {
		super();
		this.ladoIzq = ladoIzq;
		this.periodos = periodos;
	}

	public Boolean getFiltro() {
		return filtro;
	}

	public void setFiltro(Boolean filtro) {
		this.filtro = filtro;
	};

}
