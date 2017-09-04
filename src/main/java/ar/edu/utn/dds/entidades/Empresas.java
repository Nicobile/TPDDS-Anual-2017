package ar.edu.utn.dds.entidades;

import java.util.List;

import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Empresas {
	private static List<Empresa> empresas= Utilidades.getEntidad(Empresa.class);
	public static List<Empresa> getEmpresas(){
		
	
	Utilidades.closeEntityManager();
	return empresas;
		
	}
}
