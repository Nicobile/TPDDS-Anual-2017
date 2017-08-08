package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;



import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Indicador {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indicador other = (Indicador) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}


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


	public double calcular(Empresa e, Periodo periodo) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		return this.nodo.calcular(e, periodo);
	}

}
