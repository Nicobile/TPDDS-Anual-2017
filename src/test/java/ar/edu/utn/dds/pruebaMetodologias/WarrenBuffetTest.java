package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.script.ScriptException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class WarrenBuffetTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia meto;
	private Periodo periodo;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraElIndicadorException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.meto = new Metodologia("WarrenBufet");
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

		periodo = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2017, 07, 21));

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void pruebaNoHayEmpresasQueCumplen() throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoHayEmpresasQueCumplanLaCondicionException.class);

		Sumatoria maximizarROE = new Sumatoria(t.buscarIndicador("i_ROE"), t);

		/*
		 * habria que poner un periodo de 10 años, pq si no habria que poner un int
		 */
		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(maximizarROE, periodo, "mayorAmenor");

		Sumatoria minimizarnivelDeuda = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);

		/*
		 * habria que poner un periodo de 10 años, pq si no habria que poner un int
		 */
		Condicion cond2 = new OrdenaAplicandoCriterioOrdenamiento(minimizarnivelDeuda, periodo, "menorAmayor");

		Creciente margenesVtasCrecientas = new Creciente(t.buscarIndicador("i_MargenVentas"), t);

		/*
		 * ningunaEmpresa tiene margenes de ventas crecientes en los ultimos 10 años
		 */
		Condicion cond3 = new Filtro(margenesVtasCrecientas, 10);
		Longevidad longevidad = new Longevidad(t);

		Condicion cond4 = new Filtro(longevidad, 10);

		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		meto.agregarCondicion(cond3);
		meto.agregarCondicion(cond4);
		meto.aplicarMetodologia();
	}

	@Test
	public void seDebeInvertirEnFacebook() throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		Sumatoria maximizarROE = new Sumatoria(t.buscarIndicador("i_ROE"), t);

		/*
		 * habria que poner un periodo de 10 años, pq si no habria que poner un int
		 */
		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(maximizarROE, periodo, "mayorAmenor");

		Sumatoria minimizarnivelDeuda = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);

		/*
		 * habria que poner un periodo de 10 años, pq si no habria que poner un int
		 */
		Condicion cond2 = new OrdenaAplicandoCriterioOrdenamiento(minimizarnivelDeuda, periodo, "menorAmayor");

		Creciente margenesVtasCrecientas = new Creciente(t.buscarIndicador("i_MargenVentas"), t);

		Condicion cond3 = new Filtro(margenesVtasCrecientas, 3);
		Longevidad longevidad = new Longevidad(t);

		Condicion cond4 = new Filtro(longevidad, 10);

		meto.agregarCondicion(cond1);

		meto.agregarCondicion(cond2);
		meto.agregarCondicion(cond3);
		meto.agregarCondicion(cond4);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		/*
		 * la condicion 3 la cumplen facebook y twitter pero la condicion 4 la cumple
		 * facebook, como ambas filtran entonces solo facebook queda
		 */
		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Facebook"), empresas.get(0));
	}

	@After
	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
		this.meto.getCondicionesDeMetodologia().clear();
		this.meto.getPuntajeEmpresas().clear();
	}
}
