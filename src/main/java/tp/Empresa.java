package tp;

import java.util.ArrayList;

public class Empresa {
	//public LeerArchivo archivo;
	private String nombre;
	
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	private int buscarEnLista(ArrayList<Empresa> empresas, String nombreEmpresa ){
		for(int x=0;x<empresas.size();x++) {
			  //pregunto si ya existe la empresa
			if(empresas.get(x).nombre==nombreEmpresa){
			return x;	
			}
			
			}
		return -1;
	}
	
	public ArrayList<Empresa> armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo ){
		//creo lista empresas
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
		
		
		//recorro la lista que contiene todos los datos
		for(int x=0;x<lineasArchivo.size();x++) {
			
			int i;//indice de donde encuentra el elemento en la lista de empresas ya existentes
			
			i=buscarEnLista(empresas,lineasArchivo.get(x).nombreEmpresa);  
			//pregunto si ya existe la empresa
			
			if((i>=0)){//si ya existe la empresa 
				//creo una nueva cuenta
				Cuenta cuenta=new Cuenta(lineasArchivo.get(x).nombreCuenta,lineasArchivo.get(x).valorCuenta,lineasArchivo.get(x).fecha);//tomo los elementos del original y creo una cuenta para agregar a la lista
				
				empresas.get(i).cuentas.add(cuenta);//agrego la cuenta, en la lista de cuentas, de le empresa ya existente
			}
			//la empresa no existia entonces la creo
			else{
				Empresa empresa= new Empresa();
				empresa.nombre = lineasArchivo.get(x).nombreEmpresa;
				//creo la cuenta de la nueva empresa
				Cuenta cuenta=new Cuenta(lineasArchivo.get(x).nombreCuenta,lineasArchivo.get(x).valorCuenta,lineasArchivo.get(x).fecha);
				
				empresas.add(empresa);//agrego la empresa a la lista de empresas
				empresa.cuentas.add(cuenta);
				
			}
			}
		return empresas;
		
		
		
		
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
