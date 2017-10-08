package ar.edu.utn.dds.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.persistencia.Utilidades;

public class Metodologias {
	private static List<Metodologia> metodologias = new ArrayList<>();

	public static List<Metodologia> setMetodologias() {
		if (metodologias.isEmpty()) {
			metodologias = Utilidades.getEntidad(Metodologia.class);

			return metodologias;
		}
		return metodologias;

	}

	public static List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public static void persistirMetodologia(Metodologia m) {

		Utilidades.persistirUnObjeto(m);

	}

	public static void persistirCondicionesMetodologia(Metodologia m, Object objeto, List<Condicion> condiciones) {

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Metodologia metodologiaCargadaEnBase = session.find(Metodologia.class, m.getId());
			session.persist(objeto);
			condiciones.forEach(unaCond -> {
				metodologiaCargadaEnBase.getCondicionesDeMetodologia().add(unaCond);
				session.persist(unaCond);
			});

			session.merge(metodologiaCargadaEnBase);

			et.commit();
		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			throw new PersistenceException("No se pudo persistir el objeto" + e.getMessage());
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		}

	}
}
