package ar.edu.utn.dds.entidades;

import java.util.List;

import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;

//por ahora, no se si tiene mucho sentido esta clase, lo unico util que  devuelve son los indicador que se utilizan
// pero por ahi a la hara de traerme una metodologia, puedo hacer consultas a la bd y traer la metodologia ya armada
// en vez de ir armandola con los objetos que la componen
public class ValoresCalculables {
	private static List<ValorCalculable> valoresCalculables = Utilidades.getEntidad(ValorCalculable.class);
	public static List<ValorCalculable> getValoresCalculables() {

		
		Utilidades.closeEntityManager();
		return valoresCalculables;

	}

}
