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

import ar.edu.utn.dds.entidades.Cuentas;
import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class CuentaTest {

	private List<Cuenta> cuentas;
	List<Periodo> periodos;
	private Cuenta cuentaPrueba;
	private Periodo periodo;

	@Before
	public void inicializacion() {
		cuentas = new ArrayList<>();

		periodos = new ArrayList<>();
		periodo = new Periodo(LocalDate.of(1005, 04, 21), LocalDate.of(2003, 04, 21));
		cuentaPrueba = new Cuenta("c_Prueba", 200.36, periodo);
	}

	@Test
	public void actualizarCuenta() {
		cuentas = Cuentas.setCuentas();
		periodos = Periodos.setPeriodos();

		if (!periodos.contains(periodo)) {

			Periodos.persistirPeriodo(periodo);
		}
		if (!cuentas.contains(cuentaPrueba)) {

			Cuentas.persistirCuenta(cuentaPrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Cuenta cuenta = session.find(Cuenta.class, cuentaPrueba.getId());
			cuenta.setValor(500);
			session.merge(cuenta);
			et.commit();
			Cuenta cuentaDeLaBase = session.find(Cuenta.class, cuenta.getId());
			assertEquals(cuenta.getId(), cuentaDeLaBase.getId());
			assertFalse(cuentaPrueba.equals(cuentaDeLaBase));
			et.begin();
			Periodo p = session.find(Periodo.class, periodo.getId());
			session.remove(cuenta);
			session.remove(p);
			et.commit();
			cuentas.clear();
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
	public void eliminarCuenta() {
		cuentas = Cuentas.setCuentas();

		periodos = Periodos.setPeriodos();

		if (!periodos.contains(periodo)) {

			Periodos.persistirPeriodo(periodo);
		}
		if (!cuentas.contains(cuentaPrueba)) {

			Cuentas.persistirCuenta(cuentaPrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {
			et.begin();
			Cuenta cuenta = session.find(Cuenta.class, cuentaPrueba.getId());
			session.remove(cuenta);
			cuentas.remove(cuenta);
			et.commit();
			Cuenta cuentaBase = session.find(Cuenta.class, cuenta.getId());
			assertTrue(cuentaBase == null);
			cuentas.clear();
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
	public void agregarCuenta() {
		cuentas = Cuentas.setCuentas();

		periodos = Periodos.setPeriodos();
		if (!periodos.contains(periodo)) {
			Periodos.persistirPeriodo(periodo);
		}
		if (!cuentas.contains(cuentaPrueba)) {
			Cuentas.persistirCuenta(cuentaPrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {
			et.begin();
			Cuenta cuenta = session.find(Cuenta.class, cuentaPrueba.getId());
			session.remove(cuenta);
			cuentas.remove(cuenta);
			et.commit();
			Cuenta cuentaBase = session.find(Cuenta.class, cuenta.getId());
			assertTrue(cuentaBase == null);
			cuentas.clear();
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

}
