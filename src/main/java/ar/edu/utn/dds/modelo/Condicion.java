package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class Condicion {

	private ValorCalculable ladoIzq;
	private Periodo periodos;
	private int anios;
	
	public Boolean filtro;

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {

		List<PuntajeEmpresa> valoresAizq = new ArrayList<PuntajeEmpresa>();

		valoresAizq = ladoIzq.calcularValor(
				periodos,anios);/*
							 * esto va a aplicar a todas las empresas el lado
							 * izq y los guarda en la lista SIN ORDENAR
							 */
		return valoresAizq;
	}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public ValorCalculable getLadoIzq() {
		return ladoIzq;
	}

	public void setLadoIzq(ValorCalculable ladoIzq) {
		this.ladoIzq = ladoIzq;
	}

	public Periodo getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Periodo periodos) {
		this.periodos = periodos;
	}

	public Boolean getFiltro() {
		return filtro;
	}

	public void setFiltro(Boolean filtro) {
		this.filtro = filtro;
	}

}
