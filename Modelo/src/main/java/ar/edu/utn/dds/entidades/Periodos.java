package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Periodos {

	private static List<Periodo> periodos = new ArrayList<>();

	public static List<Periodo> setPeriodos() {
		if (periodos.isEmpty()) {
			periodos = Utilidades.getEntidad(Periodo.class);

			return periodos;
		}
		return periodos;

	}

	public static List<Periodo> getPeriodos() {
		return periodos;
	}

	public static void agregarPeriodo(Periodo p) {
		periodos.add(p);
	}

	public static void persistirPeriodo(Periodo p) {

		Utilidades.persistirUnObjeto(p);
		periodos.add(p);

	}

	public static void persistirPeriodoConEm(Periodo p, EntityManager em) {
		em.persist(p);
		periodos.add(p);
	}

}
