package tp;

import java.util.ArrayList;

public class Empresa {
	//public LeerArchivo archivo;
	private String nombre;
	
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	
	public void inicializate(LineaArchivo elementoColeccion){
		
		
		Empresa empresa= new Empresa();
		empresa.nombre = elementoColeccion.nombreEmpresa;
		
		//Cuenta cuenta = new Cuenta();
		Cuenta cuenta=new Cuenta(elementoColeccion.nombreCuenta,elementoColeccion.valorCuenta,elementoColeccion.fecha);
		
		empresa.cuentas.add(cuenta);
		
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
