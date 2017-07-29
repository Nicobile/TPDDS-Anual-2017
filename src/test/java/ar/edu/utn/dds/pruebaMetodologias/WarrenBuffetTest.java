package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.script.ScriptException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.FiltraYOrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.FiltroYOrdena;
import ar.edu.utn.dds.modelo.Creciente;
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
		Creciente cre = new Creciente(t.buscarIndicador("i_ROE"), t);
		LocalDate tiempo1 = LocalDate.of(2016, 3, 12);
		LocalDate tiempo2 = LocalDate.of(2015, 2, 12);
		Periodo periodocreciente = new Periodo(tiempo1,tiempo2);
		Condicion cond1 = new FiltraYOrdenaAplicandoCriterioOrdenamiento(cre, periodocreciente, "mayorAmenor");
		LocalDate tiempo3 = LocalDate.of(2016, 3, 12);
		LocalDate tiempo4 = LocalDate.of(2015, 2, 12);
		Periodo periodosumatoria = new Periodo(tiempo3,tiempo4);
		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
		Condicion cond2 = new FiltraYOrdenaAplicandoCriterioOrdenamiento(sum, periodosumatoria, "menorAmayor");
		Creciente cre2 = new Creciente(t.buscarIndicador("i_MargenVentas"), t);
		LocalDate tiempo5 = LocalDate.of(2016, 3, 12);
		LocalDate tiempo6 = LocalDate.of(2015, 2, 12);
		Periodo periodocre2 = new Periodo(tiempo5,tiempo6);
		Condicion cond3 = new FiltraYOrdenaAplicandoCriterioOrdenamiento(cre2, periodocre2, "mayorAmenor");
		Longevidad lon = new Longevidad(t.buscarIndicador("i_NivelDeuda"), t);
		LocalDate tiempo7 = LocalDate.of(2016, 3, 12);
		LocalDate tiempo8 = LocalDate.of(2015, 2, 12);
		Periodo periodolon = new Periodo(tiempo7,tiempo8);
		Condicion cond4 = new FiltroYOrdena(lon, 10.0, ">", periodolon);

		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		meto.agregarCondicion(cond3);
		meto.agregarCondicion(cond4);
	}

	@Test
	public void pruebaHayEmpresasQueCumplen() throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		assertTrue(empresas.size() > 0);
	}

	@Test
	public void seDebeInvertirEnFacebook() throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		assertEquals(empresas.get(3).getNombreEmpresa(), "Pepsico");
		assertEquals(empresas.get(2).getNombreEmpresa(), "Twitter");
		assertEquals(empresas.get(1).getNombreEmpresa(), "CocaCola");
		assertEquals(empresas.get(0).getNombreEmpresa(), "Facebook");
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
