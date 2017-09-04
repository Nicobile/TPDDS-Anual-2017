package ar.edu.utn.dds.pruebaPersistencia;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class HerenciaTest {
	
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	
@Before
public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
	this.t = new Traductor();
	this.lector = new LectorArchivo(t);
	
	this.procesador1 = new ProcesarIndicadores(t);
	
	
}
@Test
public void valorCalculable() throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
	EntityManager em= Utilidades.getEntityManager();
	EntityTransaction et= em.getTransaction();
	
	
	procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
	
	et.begin();
	
	

	ValorCalculable decr= new Decreciente(t.buscarIndicador("i_NivelDeuda"),t);
	
	em.persist(decr);
	et.commit();
	em.close();
}
}
