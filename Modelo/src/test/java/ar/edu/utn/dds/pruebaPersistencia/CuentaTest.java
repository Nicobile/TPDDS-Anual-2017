package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class CuentaTest {

	private List<Cuenta> cuentas;
	List<Periodo> periodos;
	private Cuenta cuentaPrueba;
	private Periodo periodo;
	private static final double DELTA = 1e-15;
	private EntityManager em;
	private EntityTransaction et;

	@Before
	public void inicializacion() {
		cuentas = new ArrayList<>();

		periodos = new ArrayList<>();
		periodo = new Periodo(LocalDate.of(1005, 04, 21), LocalDate.of(2003, 04, 21));
		cuentaPrueba = new Cuenta("c_Prueba", 200.36, periodo);
		em = Utilidades.getEntityManager();
		
		et = em.getTransaction();
	}

	@Test
	public void actualizarCuenta() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		et.commit();
		et.begin();
		Cuenta cuenta = em.find(Cuenta.class, cuentaPrueba.getId());
		cuenta.setValor(500);
		em.merge(cuenta);
		et.commit();
		Cuenta cuentaDeLaBase = em.find(Cuenta.class, cuenta.getId());
		assertEquals(cuenta.getId(), cuentaDeLaBase.getId());
		assertEquals(cuentaDeLaBase.getValor(), 500, DELTA);
		et.begin();
		Periodo p = em.find(Periodo.class, periodo.getId());
		em.remove(cuenta);
		em.remove(p);
		et.commit();
		cuentas.clear();
		periodos.clear();

	}

	@Test
	public void eliminarCuenta() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		et.commit();
		et.begin();
		Cuenta cuenta = em.find(Cuenta.class, cuentaPrueba.getId());
		em.remove(cuenta);
		cuentas.remove(cuenta);
		et.commit();
		Cuenta cuentaBase = em.find(Cuenta.class, cuenta.getId());
		assertTrue(cuentaBase == null);
		cuentas.clear();
		periodos.clear();

	}

	@Test
	public void agregarCuenta() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		et.commit();
		et.begin();
		Cuenta cuenta = em.find(Cuenta.class, cuentaPrueba.getId());
		em.remove(cuenta);
		cuentas.remove(cuenta);
		et.commit();
		Cuenta cuentaBase = em.find(Cuenta.class, cuenta.getId());
		assertTrue(cuentaBase == null);
		cuentas.clear();
		periodos.clear();

	}

	@After
	public void cerrarEm() {
		Utilidades.closeEntityManager();
	}

}
