package ar.edu.utn.dds.pruebaPersistencia;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.script.ScriptException;

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
	private Metodologia meto;
	private LectorArchivo lector;
	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();
		this.meto = new Metodologia("Metod");
		this.procesador1 = new ProcesarIndicadores(t);
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

	}
	@Test
	public void aplicarMetodologiaDesdeBD() throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException,
			NoSeEncuentraLaEmpresaException, NoHayEmpresasQueCumplanLaCondicionException, ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		
		procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		
		// creo la metodologia y la guardo en bd y la traigo pra verificar que de bien
		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();

		
		
		ValorCalculable decre1 = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);

		Condicion cond1 = new Filtro(decre1, 4);
		meto.agregarCondicion(cond1);
		et.begin();	
		//guardo los datos
		em.persist(decre1);
		em.persist(cond1);		
		em.persist(meto);
		et.commit();
		
		
		//los traigo
		Metodologia m=Metodologias.getMetodologias().get(0);
	//presunto por el nombre, pq dos metodologias son iguales si tienen mismo nombre
		assertEquals(m,meto);
		
//		ArrayList<PuntajeEmpresa> empresas =m.aplicarMetodologia();
//		
//		/* Solo Pepsico cumplen con la condidion de que su deuda sea decreciente */
//		assertEquals(empresas.size(), 1);
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
	}
}
