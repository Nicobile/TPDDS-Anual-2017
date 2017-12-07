package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class PeriodoTest {
	private List<Periodo> periodos;
	private EntityManager session;
	private EntityTransaction et;

	@Before
	public void inicializacion() {
		periodos = new ArrayList<>();
		session = Utilidades.getEntityManager();

		et = session.getTransaction();

		// para buscar las metodologias en bd
		CriteriaBuilder qb = session.getCriteriaBuilder();
		CriteriaQuery<Periodo> criteriaQuery = qb.createQuery(Periodo.class);
		criteriaQuery.from(Periodo.class);
		TypedQuery<Periodo> q = session.createQuery(criteriaQuery);
		periodos = q.getResultList();
	}

	@Test
	public void actualizarPeriodo() {

		Periodo periodoDePrueba = new Periodo(LocalDate.of(2001, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			et.begin();
			session.persist(periodoDePrueba);
			et.commit();
		}

		try {

			et.begin();
			Periodo periodo = session.find(Periodo.class, periodoDePrueba.getId());
			periodo.setFechaInicio(LocalDate.of(2001, 04, 21));
			periodo.setFechaFin(LocalDate.of(2002, 04, 21));

			session.merge(periodo);

			et.commit();
			Periodo periodoDeLaBase = session.find(Periodo.class, periodo.getId());
			assertEquals(periodoDePrueba.getId(), periodoDeLaBase.getId());
			assertTrue(periodoDePrueba.equals(periodoDeLaBase));
			et.begin();
			session.remove(periodo);
			periodos.remove(periodo);
			et.commit();
			periodos.clear();

		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			throw new PersistenceException("No se pudo actualizar el objeto" + e.getMessage());
		}

	}

	@Test
	public void eliminarPeriodo() {

		Periodo periodoDePrueba = new Periodo(LocalDate.of(2002, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			et.begin();
			session.persist(periodoDePrueba);
			et.commit();

		}

		try {
			et.begin();
			Periodo periodo = session.find(Periodo.class, periodoDePrueba.getId());

			session.remove(periodo);
			periodos.remove(periodo);
			et.commit();
			Periodo periodoDeLaBase = session.find(Periodo.class, periodo.getId());
			assertTrue(periodoDeLaBase == null);
			periodos.clear();

		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			throw new PersistenceException("No se pudo actualizar el objeto" + e.getMessage());
		}

	}

	@Test
	public void agregarPeriodo() {

		Periodo periodoDePrueba = new Periodo(LocalDate.of(1001, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			et.begin();
			session.persist(periodoDePrueba);
			et.commit();
		}

		try {
			et.begin();
			Periodo periodo = session.find(Periodo.class, periodoDePrueba.getId());

			session.remove(periodo);
			periodos.remove(periodo);
			et.commit();
			Periodo periodoDeLaBase = session.find(Periodo.class, periodo.getId());
			assertTrue(periodoDeLaBase == null);
			periodos.clear();

		} catch (PersistenceException e) {
			if (et != null) {
				et.rollback();
			}
			throw new PersistenceException("No se pudo actualizar el objeto" + e.getMessage());
		}

	}

	@After
	public void cerrarEm() {
		Utilidades.closeEntityManager();
	}

}
