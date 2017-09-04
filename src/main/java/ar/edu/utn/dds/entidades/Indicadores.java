package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Indicadores {
	private static List<Indicador> indicadores= new ArrayList<>();
	public static List<Indicador> getIndicadores() {
		if(indicadores.isEmpty()) {
		indicadores= Utilidades.getEntidad(Indicador.class);
		Utilidades.closeEntityManager();
		return indicadores;}
		return indicadores;

	}
}
