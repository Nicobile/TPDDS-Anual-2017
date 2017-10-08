package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class PeriodoTest {
	private List<Periodo> periodos;

	@Before
	public void inicializacion() {
		periodos = new ArrayList<>();


	}


	@Test
	public void actualizarPeriodo() {
		periodos=Periodos.setPeriodos();
		Periodo periodoDePrueba = new Periodo(LocalDate.of(2001, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			Periodos.persistirPeriodo(periodoDePrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Periodo periodo = session.find(Periodo.class, periodoDePrueba.getId());
			periodo.setFechaInicio(LocalDate.of(2001, 04, 21));
			periodo.setFechaFin(LocalDate.of(2002, 04, 21));

			session.merge(periodo);

			et.commit();
			Periodo periodoDeLaBase = session.find(Periodo.class, periodo.getId());
			assertEquals(periodoDePrueba.getId(), periodoDeLaBase.getId());
			assertFalse(periodoDePrueba.equals(periodoDeLaBase));
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
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		}

	}

	@Test
	public void eliminarPeriodo() {
periodos=Periodos.setPeriodos();
		Periodo periodoDePrueba = new Periodo(LocalDate.of(2002, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			Periodos.persistirPeriodo(periodoDePrueba);

		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
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
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		}

	}

	@Test
	public void agregarPeriodo() {
		periodos=Periodos.setPeriodos();
		Periodo periodoDePrueba = new Periodo(LocalDate.of(1001, 04, 21), LocalDate.of(2003, 04, 21));
		if (!periodos.contains(periodoDePrueba)) {
			Periodos.persistirPeriodo(periodoDePrueba);
		}
		
		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
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
		} finally {
			if (session != null) {
				Utilidades.closeEntityManager();
			}
		
		
	}}
	
}

