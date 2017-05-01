package ar.edu.utn.dds.PROCESARARCHIVO;

import java.util.ArrayList;

public class Empresa {
	//public LeerArchivo archivo;
	private String nombre;
	
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	public Empresa(  String nombre,ArrayList<Cuenta> cuentas) {
        this.nombre= nombre;
        
        this.cuentas=cuentas;}
	
	
	
	

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
