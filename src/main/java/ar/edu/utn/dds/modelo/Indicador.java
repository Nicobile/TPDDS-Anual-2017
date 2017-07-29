package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Indicador {
	private String nombre;
	private String operacion;

	private NodoIndicador nodo;
	// ver de sacar la fecha

	public NodoIndicador getNodo() {
		return nodo;
	}

	public void setNodo(NodoIndicador nodo) {
		this.nodo = nodo;
	}

	public Indicador(String nombre, String operacion) {

		this.nombre = nombre;
		this.operacion = operacion;

	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double calcular(Empresa e, Periodo periodo)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		return this.nodo.calcular(e, periodo);
	}

}
