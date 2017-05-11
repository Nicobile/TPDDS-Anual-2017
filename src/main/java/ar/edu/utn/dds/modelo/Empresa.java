package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

public class Empresa {
	// public LeerArchivo archivo;
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
	public Cuenta buscarUnaCuentaPorFecha(String nombreDeCuenta,String fecha) {
		try {
			for (int i = 0; i < getCuentas().size(); i++) {
				if (getCuentas().get(i).getNombre().equals(nombreDeCuenta) && getCuentas().get(i).getFecha().equals(fecha)) {
					return this.getCuentas().get(i);
				}
			}
		} catch (Exception e) {
			System.out.println("NO SE ENCONTRO LA CUENTA EN DICHA FECHA PARA ESA EMPRESA");
		}
		;

		return null;
	}
	public int obtenerValorDeCuenta(String nombreDeCuenta, String fecha){
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
