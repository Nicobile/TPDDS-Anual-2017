package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class IndicadoresTest {
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private LectorArchivo lector;
	private Periodo p;
	private Indicador indPrueba;
	private List<Indicador> indicadores;
	private static final double DELTA = 1e-15;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraElIndicadorException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		indicadores = new ArrayList<Indicador>();
		indPrueba = new Indicador("i_indicadorprueba", "i_ROE+1");
		p = new Periodo(LocalDate.of(2013, 04, 21), LocalDate.of(2018, 04, 21));
	}

	@Test
	public void buscoIndicadoresCargadosEnBD() throws NoSeEncuentraElIndicadorException {
		List<Indicador> indicadoresPersistidos = Indicadores.setIndicadores();

		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_ROE")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_NivelDeuda")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_MargenVentas")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_IndicadorD")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_IngresoNeto")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("j_IndicadorF")));
		assertTrue(indicadoresPersistidos.contains(t.buscarIndicador("i_Solvencia")));
	}

	@Test
	public void calculoIndicadorEnBD() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		List<Indicador> indicadoresPersistidos = Indicadores.setIndicadores();
		List<Empresa> empresasPersistidas = Empresas.setEmpresas();
		indicadoresPersistidos.stream().forEach(unI -> {
			if (!t.getIndicadores().contains(unI)) {
				t.getIndicadores().add(unI);
			}
		});

		empresasPersistidas.stream().forEach(unaE -> {
			if (!t.getEmpresas().contains(unaE)) {
				t.getEmpresas().add(unaE);
			}
		});
		assertEquals(t.calcular("Pepsico", p, "i_ROE"), 12.740526524223508, DELTA);
	}

	@Test
	public void actualizarIndicador() {
		indicadores = Indicadores.setIndicadores();

		if (!indicadores.contains(indPrueba)) {

			Indicadores.persistirIndicador(indPrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {

			et.begin();
			Indicador indicador = session.find(Indicador.class, indPrueba.getId());
			indPrueba.setOperacion("i_ROE + 2");
			session.merge(indicador);
			et.commit();
			Indicador indicadorDeLaBase = session.find(Indicador.class, indicador.getId());
			assertEquals(indicador.getId(), indicadorDeLaBase.getId());
			assertFalse(indPrueba.equals(indicadorDeLaBase));
			et.begin();
			session.remove(indicador);
			et.commit();
			indicadores.clear();

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
		indicadores = Indicadores.setIndicadores();

		if (!indicadores.contains(indPrueba)) {

			Indicadores.persistirIndicador(indPrueba);
		}

		EntityManager session = Utilidades.getEntityManager();
		EntityTransaction et = session.getTransaction();
		try {
			et.begin();
			Indicador indicador = session.find(Indicador.class, indPrueba.getId());
			session.remove(indicador);
			indicadores.remove(indicador);
			et.commit();
			Indicador indicadorBase = session.find(Indicador.class, indicador.getId());
			assertTrue(indicadorBase == null);
			indicadores.clear();

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

	@Test(expected = PersistenceException.class)
	public void persistirIndicadorYaExistente() {
		Indicador i = new Indicador("i_NivelDeuda", "1+2+3");
		Utilidades.persistirUnObjeto(i);
	}

}
