package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Periodos {

	private static 	List<Periodo> periodos=new ArrayList<>();
	public static List<Periodo> getPeriodos(){
		if(periodos.isEmpty()) {
			periodos=Utilidades.getEntidad(Periodo.class);
			Utilidades.closeEntityManager();
			return periodos;
		}
		return periodos;
		
	}
}
