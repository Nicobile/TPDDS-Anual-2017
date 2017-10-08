package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.entidades.Metodologias;
import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class MetodologiaTest {
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia metod;
	private LectorArchivo lector;
	private Periodo periodo;
	private List<Metodologia> metodologiasPersistidos;
	private static Logger log = Logger.getLogger(Principal.class);

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraElIndicadorException {

		this.t = new Traductor();
		this.metod = new Metodologia("testMetodotest");
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		metodologiasPersistidos = Metodologias.setMetodologias();
		periodo = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2017, 07, 21));

		Sumatoria sum = new Sumatoria(Indicadores.getIndicadores().stream()
				.filter(unI -> unI.getNombre().equals("i_NivelDeuda")).findFirst().get(), t);

		Periodo p = new Periodo();
		try {

			p = Periodos.getPeriodos().stream().filter(unP -> unP.equals(periodo)).findFirst().get();
		} catch (NoSuchElementException e) {
			p = periodo;
			Utilidades.persistirUnObjeto(p);
			Periodos.agregarPeriodo(p);

		}
		Condicion condicion1 = new FiltroSegunEcuacion(sum, 38, ">", p);
		Condicion condicion2 = new OrdenaAplicandoCriterioOrdenamiento(sum, p, "menorAmayor");

		metod.agregarCondicion(condicion1);
		metod.agregarCondicion(condicion2);

		try {
			
			EntityManager em = Utilidades.getEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(sum);
			em.persist(condicion1);
			em.persist(condicion2);
			em.persist(metod);
			et.commit();
			metodologiasPersistidos.add(metod);
		} catch (PersistenceException e) {
			log.fatal("La metodologia ya esta cargada");
		}
		Utilidades.closeEntityManager();

	}

	@Test(expected = PersistenceException.class)
	// se evalua ingresar el mismo nombre de metodologia
	// sale error "Ya existe una metodologia con ese nombre"
	public void crearMismaMetodologiaDesdeBD() {

		Metodologia metodaux = new Metodologia("testMetodotest");
		// realizo la persistencia del objeto metodologia
		Metodologias.persistirMetodologia(metodaux);
		

	}

	@Test
	public void aplicarMetodologiaEnBD()
			throws NoHayEmpresasQueCumplanLaCondicionException, NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		System.out.println(metodologiasPersistidos.size());
		metodologiasPersistidos.forEach(unaMeto -> {

			if (!t.getMetodologias().contains(unaMeto)) {
				t.agregarMetodologia(unaMeto);
			}

			unaMeto.getCondicionesDeMetodologia().stream().forEach(unC -> {
				unC.getLadoIzq().setTraductor(t);
			});
		});

		Metodologia metoAplicar = t.buscarMetodologia("testMetodotest");

		ArrayList<PuntajeEmpresa> empresas = metoAplicar.aplicarMetodologia(t.getEmpresas());
		// De un test anterior conozco los resultados
		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Facebook"), empresas.get(1));
		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
	}

	@After
	public void eliminarListas() {
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
		this.metod.getCondicionesDeMetodologia().clear();
		this.metod.getPuntajeEmpresas().clear();
	}
}
