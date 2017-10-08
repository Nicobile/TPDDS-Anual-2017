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
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.persistencia.Utilidades;

public class EmpresaTest {
	private List<Cuenta> cuentasEmpresa;
	List<Empresa> empresas;
	List<Periodo> periodos;
	private Cuenta cuentaPrueba;
	private Periodo periodo;
	private Empresa empresaPrueba;

	@Before
	public void inicializacion() {
		cuentasEmpresa = new ArrayList<>();

		periodos = new ArrayList<>();
		periodo = new Periodo(LocalDate.of(1005, 04, 21), LocalDate.of(2003, 04, 21));
		cuentaPrueba = new Cuenta("c_Prueba", 200.36, periodo);
		cuentasEmpresa.add(cuentaPrueba);
		empresaPrueba = new Empresa("Prueba", cuentasEmpresa);
	}

	@Test
	public void actualizarEmpresa() {
		Periodos.persistirPeriodo(periodo);
		Cuentas.persistirCuenta(cuentaPrueba);
		Empresas.persistirEmpresa(empresaPrueba);

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Empresa empresa = session.find(Empresa.class, empresaPrueba.getId());
			empresa.setNombre("Empresa");
			;
			session.merge(empresa);
			et.commit();
			Empresa empresaDeLaBase = session.find(Empresa.class, empresa.getId());
			assertEquals(empresa.getId(), empresaDeLaBase.getId());
			assertFalse(empresaPrueba.equals(empresaDeLaBase));
			et.begin();

			session.remove(empresaDeLaBase);

			et.commit();

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
	public void eliminarEmpresa() {

		Periodos.persistirPeriodo(periodo);
		Cuentas.persistirCuenta(cuentaPrueba);
		Empresas.persistirEmpresa(empresaPrueba);

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {
			et.begin();

			Empresa empresa = session.find(Empresa.class, empresaPrueba.getId());
			session.remove(empresa);
			et.commit();

			Empresa empresaBase = session.find(Empresa.class, empresa.getId());
			assertTrue(empresaBase == null);

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
		Periodos.persistirPeriodo(periodo);
		Cuentas.persistirCuenta(cuentaPrueba);
		Empresas.persistirEmpresa(empresaPrueba);
		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {
			et.begin();
			Empresa empresa = session.find(Empresa.class, empresaPrueba.getId());
			session.remove(empresa);

			et.commit();

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
