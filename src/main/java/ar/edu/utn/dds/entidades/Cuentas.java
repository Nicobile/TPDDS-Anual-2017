package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Cuentas {
private static List<Cuenta> cuentas= new ArrayList<>();
	public static List<Cuenta> getCuentas(){
		
		if(cuentas.isEmpty()) {
		cuentas=Utilidades.getEntidad(Cuenta.class);
		Utilidades.closeEntityManager();
		return cuentas;
		}
		
		return cuentas;
	
}

}
