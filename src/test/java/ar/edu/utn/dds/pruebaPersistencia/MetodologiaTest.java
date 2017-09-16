package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.entidades.Metodologias;
import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class MetodologiaTest {
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia metod;
	private LectorArchivo lector;
	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();
		this.metod = new Metodologia("Metod");
		this.procesador1 = new ProcesarIndicadores(t);
		//this.lector = new LectorArchivo(t);
		//this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

	}
	@Test
	//se ingresa en la base de datos la metodologia y se busca la misma metodologia.
	public void crearMetodologiaDesdeBD(){
		
		t.agregarMetodologia(metod);
		
		//realizo la persistencia del objeto metodologia
		Utilidades.persistirUnObjeto(metod);
		
		Metodologia m = t.buscarMetodologia("Metod");
		assertEquals(metod,m);
	}
	
	@Test(expected=PersistenceException.class)
	//se evalua ingresar el mismo nombre de metodologia 
	//sale error "Ya existe una metodologia con ese nombre"
	public void crearMismaMetodologiaDesdeBD(){
		
		metod = new Metodologia("Metod");
		
		t.agregarMetodologia(metod);
		
		//realizo la persistencia del objeto metodologia
		Utilidades.persistirUnObjeto(metod);

	}
}
