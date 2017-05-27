package ar.edu.utn.dds.modelo;

import excepciones.NoSeEncuentraEnLaLista;

public class Indicador {
	private String nombre;
	private Operacion operacion;

	private NodoIndicador nodo;
	// ver de sacar la fecha

	public NodoIndicador getNodo() {
		return nodo;
	}

	public void setNodo(NodoIndicador nodo) {
		this.nodo = nodo;
	}

	public Indicador(String nombre, Operacion operacion) {

		this.nombre = nombre;
		this.operacion = operacion;

	}

	public String getOperacion() {
		return operacion.getOperacion();
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double calcular(Empresa e, String fecha) throws NoSeEncuentraEnLaLista {
		return this.nodo.calcular(e, fecha);
	}

}
