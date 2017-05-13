package ar.edu.utn.dds.modelo;

public class Cuenta implements Operando{
	private String fecha;
	private String nombre;
	private int valor;

	public Cuenta(String nombreCuenta, int valorCuenta, String fecha) {
		this.nombre = nombreCuenta;
		this.valor = valorCuenta;
		this.fecha = fecha;
	}

	/*
	 * public void inicializate(LineaArchivo elementoColeccion){ Cuenta cuenta=
	 * new Cuenta(); cuenta.fecha=elementoColeccion.fecha;
	 * cuenta.valor=elementoColeccion.valorCuenta;
	 * cuenta.nombre=elementoColeccion.nombreCuenta; }
	 */

	public String getFecha() {
		return fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + valor;
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
		Cuenta other = (Cuenta) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int calcular() {
		// TODO Auto-generated method stub
		return this.getValor();
	}
	

}
