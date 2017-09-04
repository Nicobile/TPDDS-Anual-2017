package ar.edu.utn.dds.entidades;

import java.util.List;

import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Periodos {

	private static 	List<Periodo> periodos=Utilidades.getEntidad(Periodo.class);
	public static List<Periodo> getPeriodos(){
		
	
		Utilidades.closeEntityManager();
		return periodos;
		
	}
}
