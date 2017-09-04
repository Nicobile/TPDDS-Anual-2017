package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Empresas {
	private static List<Empresa> empresas=new ArrayList<>();
	public static List<Empresa> getEmpresas(){
		if(empresas.isEmpty()) {
			empresas= Utilidades.getEntidad(Empresa.class);
			Utilidades.closeEntityManager();
			return empresas;
		}
		return empresas;
		
		
	}

}
