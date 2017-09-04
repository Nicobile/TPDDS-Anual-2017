package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Metodologias {
	private static List<Metodologia> metodologias= new ArrayList<>();
	
	public static List<Metodologia> getMetodologias() {
		if(metodologias.isEmpty()) {
		metodologias= Utilidades.getEntidad(Metodologia.class);
		Utilidades.closeEntityManager();
		return metodologias;}
		return metodologias;

	}

}
