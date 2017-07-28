package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class Condicion {

	private ValorCalculable ladoIzq;
	private int periodos;

	public Boolean filtro;

	/*
	 * 3 formas de ingresar por interfaz lado izquierdo derecho comparador y
	 * periodos lado izquierdo periodos y criterio lado izquierdo y periodos
	 */

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
			NoSeEncuentraElIndicadorException {

		List<PuntajeEmpresa> valoresAizq = new ArrayList<PuntajeEmpresa>();

		valoresAizq = ladoIzq.calcularValor(
				periodos);/*
							 * esto va a aplicar a todas las empresas el lado
							 * izq y los guarda en la lista SIN ORDENAR
							 */
		return valoresAizq;
	}

	public ValorCalculable getLadoIzq() {
		return ladoIzq;
	}

	public void setLadoIzq(ValorCalculable ladoIzq) {
		this.ladoIzq = ladoIzq;
	}

	public int getPeriodos() {
		return periodos;
	}

	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}

	public Boolean getFiltro() {
		return filtro;
	}

	public void setFiltro(Boolean filtro) {
		this.filtro = filtro;
	}

}
