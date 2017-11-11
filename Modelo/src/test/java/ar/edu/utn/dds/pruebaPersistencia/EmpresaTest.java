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
	private EntityManager em;
	private EntityTransaction et;

	@Before
	public void inicializacion() {
		cuentasEmpresa = new ArrayList<>();

		periodos = new ArrayList<>();
		periodo = new Periodo(LocalDate.of(1005, 04, 21), LocalDate.of(2003, 04, 21));
		cuentaPrueba = new Cuenta("c_Prueba", 200.36, periodo);
		cuentasEmpresa.add(cuentaPrueba);
		empresaPrueba = new Empresa("Prueba", cuentasEmpresa);
		
		em = Utilidades.getEntityManager();

		et = em.getTransaction();
	}

	@Test
	public void actualizarEmpresa() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		em.persist(empresaPrueba);
		et.commit();
		et.begin();
		Empresa empresa = em.find(Empresa.class, empresaPrueba.getId());
		empresa.setNombre("Empresa");
		em.merge(empresa);
		et.commit();
		Empresa empresaDeLaBase = em.find(Empresa.class, empresa.getId());
		assertEquals(empresa.getId(), empresaDeLaBase.getId());

		assertTrue(empresaDeLaBase.getNombre().equals("Empresa"));
		et.begin();

		em.remove(empresaDeLaBase);

		et.commit();

	}

	@Test
	public void eliminarEmpresa() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		em.persist(empresaPrueba);
		et.commit();
		et.begin();
		Empresa empresa = em.find(Empresa.class, empresaPrueba.getId());
		em.remove(empresa);
		et.commit();
		Empresa empresaBase = em.find(Empresa.class, empresa.getId());
		assertTrue(empresaBase == null);

	}

	@Test
	public void agregarEmpresa() {

		et.begin();
		em.persist(periodo);
		em.persist(cuentaPrueba);
		em.persist(empresaPrueba);
		et.commit();
		et.begin();
		Empresa empresa = em.find(Empresa.class, empresaPrueba.getId());
		em.remove(empresa);

		et.commit();

	}

	@After
	public void cerrarEm() {
		Utilidades.closeEntityManager();
	}

}
