package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

@Entity
@Table(name = "indicadores")
public class Indicador {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nombre;
	private String operacion;
	private static NodoIndicador nodo;

	public double calcular(Empresa e, Periodo periodo) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		return nodo.calcular(e, periodo);
	}
	public Indicador() {
		
	}

	public Indicador(String nombre, String operacion) {
		this.nombre = nombre;
		this.operacion = operacion;
	}

	public NodoIndicador getNodo() {
		return nodo;
	}

	public void setNodo(NodoIndicador no) {
		nodo = no;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
