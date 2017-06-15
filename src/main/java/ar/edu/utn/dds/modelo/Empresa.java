package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Empresa {

	private String nombre;

	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

	public Empresa(String nombre, ArrayList<Cuenta> cuentas) {
		this.nombre = nombre;

		this.cuentas = cuentas;
	}

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) throws NoSeEncuentraLaCuenta {

		if (this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta)).findFirst()
				.isPresent()) {

			return this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
					.findFirst().get();
		}

		throw new NoSeEncuentraLaCuenta("No se encuentra la cuenta");

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

	public Cuenta buscarUnaCuentaPorFecha(String nombreDeCuenta, String fecha)
			throws NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		if (this.getCuentas().stream()
				.filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getFecha().equals(fecha))
				.findFirst().isPresent()) {
			return this.getCuentas().stream().filter(
					unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getFecha().equals(fecha))
					.findFirst().get();
		} else {
			if (!this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
					.findFirst().isPresent()) {
				throw new NoSeEncuentraLaCuenta("No se encuentra la cuenta");
			} else {
				throw new NoSeEncuentraLaCuentaEnEsaFecha(
						"No se encontro para la empresa una cuenta en la fecha especificada ");
			}
		}

	}

	public void filtraCuentasEnPeriodo(String p) {
		this.getCuentas().stream().filter(unaC -> unaC.getFecha().equals(p));
	}

	public double obtenerValorDeCuenta(String nombreDeCuenta, String fecha)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
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

	public int obtenerLaFechaDeLaCuentaMasAntigua() {
		int fechaMasAntigua = 0;
		int xx = Integer.valueOf(this.getCuentas().get(0).getFecha());
		for (int i = 0; i < this.getCuentas().size(); i++) {

			if (Integer.valueOf(this.getCuentas().get(i).getFecha()) <= xx) {
				fechaMasAntigua = Integer.valueOf(getCuentas().get(i).getFecha());
				xx = Integer.valueOf(getCuentas().get(i).getFecha());
			}
		}
		return fechaMasAntigua;
	}

}
