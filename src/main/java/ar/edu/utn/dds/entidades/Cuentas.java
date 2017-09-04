package ar.edu.utn.dds.entidades;

import java.util.List;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Cuentas {
private static List<Cuenta> cuentas=Utilidades.getEntidad(Cuenta.class);
	public static List<Cuenta> getCuentas(){
	
Utilidades.closeEntityManager();
	return cuentas;
	
}

}
