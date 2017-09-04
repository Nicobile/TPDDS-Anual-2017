package ar.edu.utn.dds.pruebaPersistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class HerenciaTest {

	private ProcesarIndicadores procesador1;
	private Traductor t;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();

		this.procesador1 = new ProcesarIndicadores(t);

	}

	@Test
	public void valorCalculable() throws NoSeEncuentraElIndicadorException, FileNotFoundException, IOException,
			NoSeEncuentraLaEmpresaException {
		
		procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();



		et.begin();

		ValorCalculable decr = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);

		em.persist(decr);
		et.commit();
		Utilidades.closeEntityManager();
	}

	@Test
	public void condiciones() throws NoSeEncuentraElIndicadorException, NoHayEmpresasQueCumplanLaCondicionException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, FileNotFoundException,
			IOException {
		procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		ValorCalculable decre = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);
		em.persist(decre);
		Condicion cond = new Filtro(decre, 4);
		em.persist(cond);
		et.commit();
		Utilidades.closeEntityManager();

	}

	@Test
	public void DosCondiciones() throws NoSeEncuentraElIndicadorException, NoHayEmpresasQueCumplanLaCondicionException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, FileNotFoundException,
			IOException {
		procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();

		Periodo periodo = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2017, 07, 21));
		et.begin();

		ValorCalculable sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);

		em.persist(sum);
		em.persist(periodo);
		Condicion cond = new FiltroSegunEcuacion(sum, 400, "<", periodo);
		Condicion cond2 = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, "mayorAmenor");
		em.persist(cond);
		em.persist(cond2);

		et.commit();

		Utilidades.closeEntityManager();

	}

}
