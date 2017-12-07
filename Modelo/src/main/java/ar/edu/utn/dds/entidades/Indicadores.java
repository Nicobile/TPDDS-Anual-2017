package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Indicadores {
	private static List<Indicador> indicadores = new ArrayList<>();

	public static List<Indicador> setIndicadores() {
		if (indicadores.isEmpty()) {
			indicadores = Utilidades.getEntidad(Indicador.class);

			return indicadores;
		}
		return indicadores;

	}

	public static List<Indicador> getIndicadores() {
		return indicadores;
	}

	public static void agregarIndicador(Indicador i) {
		indicadores.add(i);

	}

	public static void persistirIndicador(Indicador i) {

		Utilidades.persistirUnObjeto(i);
		indicadores.add(i);

	}

	public static void persistirIndicadores(List<Indicador> ind) {
		Indicadores.setIndicadores();

		EntityManager entityManager = Utilidades.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			ind.stream().forEach(unI -> {

				if (!(indicadores.contains(unI))) {
					entityManager.persist(unI);
					indicadores.add(unI);
				}
			});
			transaction.commit();
		} catch (PersistenceException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new PersistenceException("No se pudo persistir el objeto" + e.getMessage());
		} finally {
			if (entityManager != null) {
				Utilidades.closeEntityManager();
			}

		}

	}
}
