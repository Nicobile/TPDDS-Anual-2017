package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import antlr.ExpressionParser;

public class Empresa {
	
	private String nombre;

	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

	public Empresa(String nombre, ArrayList<Cuenta> cuentas) {
		this.nombre = nombre;

		this.cuentas = cuentas;
	}

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) {
		try {
			for (int i = 0; i < getCuentas().size(); i++) {
				if (getCuentas().get(i).getNombre().equals(nombreDeCuenta)) {
					return this.getCuentas().get(i);
				}
			}
		} catch (Exception e) {
			System.out.println("NO SE ENCONTRO LA CUENTA PARA ESA EMPRESA");
		}
		;

		return null;
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

	public Cuenta buscarUnaCuentaPorFecha(String nombreDeCuenta, String fecha) {
		try {
			for (int i = 0; i < getCuentas().size(); i++) {
				if ((getCuentas().get(i).getNombre().equals(nombreDeCuenta))
						&& (getCuentas().get(i).getFecha().equals(fecha)) ){
					return this.getCuentas().get(i);
				}
			}
		} catch (Exception e) {
			System.out.println("NO SE ENCONTRO LA CUENTA EN DICHA FECHA PARA ESA EMPRESA");
		}
		;

		return null;
	}

	public int obtenerValorDeCuenta(String nombreDeCuenta, String fecha) {
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
