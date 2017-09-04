package ar.edu.utn.dds.entidades;

import java.util.List;

import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Indicadores {
	private static List<Indicador> indicadores= Utilidades.getEntidad(Indicador.class);
	public static List<Indicador> getIndicadores() {
		
		Utilidades.closeEntityManager();
		return indicadores;

	}
}
