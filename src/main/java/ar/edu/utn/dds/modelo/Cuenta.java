package ar.edu.utn.dds.modelo;

public class Cuenta {
	private Periodo periodo;
	private String nombre;
	private double valor;

	public Cuenta(String nombreCuenta, double valorCuenta, Periodo periodo) {
		this.nombre = nombreCuenta;
		this.valor = valorCuenta;
		this.periodo = periodo;
	}



	public Periodo getPeriodo() {
		return periodo;
	}



	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
