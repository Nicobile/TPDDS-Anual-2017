package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import excepciones.NoSeEncuentraCuenta;
import excepciones.NoSeEncuentraLaEmpresa;
import excepciones.NoSeEncuentraLaCuentaEnEsaFecha;

public class Empresa {

	private String nombre;

	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

	public Empresa(String nombre, ArrayList<Cuenta> cuentas) {
		this.nombre = nombre;

		this.cuentas = cuentas;
	}

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) throws NoSeEncuentraCuenta {

		if (this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta)).findFirst()
				.isPresent()) {

			return this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
					.findFirst().get();
		}

		throw new NoSeEncuentraCuenta("No se encuentra la cuenta");

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuentas == null) ? 0 : cuentas.hashCode());
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
		Empresa other = (Empresa) obj;
		if (cuentas == null) {
			if (other.cuentas != null)
				return false;
		} else if (!cuentas.equals(other.cuentas))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public Cuenta buscarUnaCuentaPorFecha(String nombreDeCuenta, String fecha) throws  NoSeEncuentraCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		if (this.getCuentas().stream()
				.filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getFecha().equals(fecha))
				.findFirst().isPresent()) {
			return this.getCuentas().stream().filter(
					unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getFecha().equals(fecha))
					.findFirst().get();}
		else{
		if(!this.getCuentas().stream()
				.filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) )
				.findFirst().isPresent()){
			throw new NoSeEncuentraCuenta("No se encuentra la cuenta");
		}
		else{
			throw new NoSeEncuentraLaCuentaEnEsaFecha("No se encontro para la empresa una cuenta en la fecha especificada ");
		}
		}
			
				

	}

	public double obtenerValorDeCuenta(String nombreDeCuenta, String fecha) throws NoSeEncuentraLaEmpresa, NoSeEncuentraCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		return this.buscarUnaCuentaPorFecha(nombreDeCuenta, fecha).getValor();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

}
