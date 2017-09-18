package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.script.ScriptException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Metodologias;
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
import ar.edu.utn.dds.modelo.Promedio;
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
	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException, NoSeEncuentraElIndicadorException {
		
		
		this.t = new Traductor();
		this.metod = new Metodologia("testMetodotest");
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		periodo = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2017, 07, 21));
		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"), t);
		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);

		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(prom, periodo, "menorAmayor");
		Condicion cond2 = new FiltroSegunEcuacion(sum, 38, ">", periodo);
		metod.agregarCondicion(cond1);
		metod.agregarCondicion(cond2);
		List<Metodologia> metodologiasPersistidos = Metodologias.setMetodologias();
		if(!metodologiasPersistidos.contains(metod)) {
			EntityManager em=Utilidades.getEntityManager();
			EntityTransaction et=em.getTransaction();
			et.begin();
			em.persist(periodo);
			em.persist(prom);
			em.persist(sum);
			em.persist(cond1);
			em.persist(cond2);
			em.persist(metod);
			et.commit();
			Utilidades.closeEntityManager();
			
		}
		
		
		metodologiasPersistidos.forEach(unaMeto -> {
			
			if (!t.getMetodologias().contains(unaMeto)) {
				t.agregarMetodologia(unaMeto);
			}
			unaMeto.getCondicionesDeMetodologia().stream().forEach(unC -> {
				unC.getLadoIzq().setTraductor(t);
			});
		});
	}
	@Test
	public void igualdadMetodologiaBDyCreada(){
		List<Metodologia> metodologiasPersistidos = Metodologias.setMetodologias();
		assertTrue(metodologiasPersistidos.contains(metod));
	}
	
	@Test(expected=PersistenceException.class)
	//se evalua ingresar el mismo nombre de metodologia 
	//sale error "Ya existe una metodologia con ese nombre"
	public void crearMismaMetodologiaDesdeBD(){
		
		Metodologia metodaux = new Metodologia("testMetodotest");
		//realizo la persistencia del objeto metodologia
		Utilidades.persistirUnObjeto(metodaux);

	}
	
	@Test
	public void aplicarMetodologiaEnBD() throws NoHayEmpresasQueCumplanLaCondicionException, NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		Metodologia metoAplicar = t.buscarMetodologia("testMetodotest");
		ArrayList<PuntajeEmpresa> empresas = metoAplicar.aplicarMetodologia(t.getEmpresas());
		//De un test anterior conozco los resultados
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
