package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
import ar.edu.utn.dds.modelo.FiltraYOrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Promedio;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class PruebaMetodologiaTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia meto;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraElIndicadorException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.meto = new Metodologia("Metod");
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void pruebaSoloCondicionDecreciente() throws NoSeEncuentraElIndicadorException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		Periodo periodo = new Periodo("21/04/2010", "21/07/2017");
		Decreciente decre = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);

		Condicion cond = new Filtro(decre, periodo, 4);

		meto.agregarCondicion(cond);

		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();

		// Solo Pepsico cumplen con la condidion de que su deuda sea decreciente
		assertEquals(empresas.size(), 1);
		assertEquals(empresas.get(0).getNombreEmpresa(), "Pepsico");

	}

	@Test
	public void pruebaSoloCondicionCreciente() throws NoSeEncuentraElIndicadorException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		Periodo periodo = new Periodo("21/04/2010", "21/07/2017");
		Creciente creciente = new Creciente(t.buscarIndicador("i_NivelDeuda"), t);

		Condicion cond = new Filtro(creciente, periodo, 4);

		meto.agregarCondicion(cond);

		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();

		// Solo Twitter, Cocacola y Facebook cumplen con la condidion de que su
		// deuda sea decreciente
		assertEquals(empresas.size(), 3);
		assertEquals(empresas.get(0).getNombreEmpresa(), "Facebook");
		assertEquals(empresas.get(1).getNombreEmpresa(), "CocaCola");
		assertEquals(empresas.get(2).getNombreEmpresa(), "Twitter");

	}

	@Test
	public void pruebaSoloCondicionSumatoria() throws NoSeEncuentraElIndicadorException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
		Periodo periodo = new Periodo("21/04/2010", "21/07/2017");

		Condicion cond2 = new FiltroSegunEcuacion(sum, 400.0, ">", periodo);
		meto.agregarCondicion(cond2);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		assertEquals(empresas.size(), 1);

	}

	@Test
	public void pruebaSoloCondicionLongevidad() throws NoSeEncuentraElIndicadorException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		Longevidad lon = new Longevidad(t);
		Periodo periodo = new Periodo("21/04/2010", "21/07/2017");
		Filtro condlon = new Filtro(lon, periodo, 20);
		meto.agregarCondicion(condlon);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia(); // Solo
																		// CocacolaYpesico
																		// tienen
																		// mas
																		// de
																		// 20"
		assertEquals(empresas.size(), 2);

	}

	@Test
	public void pruebaSoloPromedio()throws
	  NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
	 ScriptException, NoSePudoOrdenarLaCondicionException,
	  NoSeEncuentraLaCuentaException,
	  NoSeEncuentraLaCuentaEnElPeriodoException{ 
		 Promedio pro = new Promedio(t.buscarIndicador("i_NivelDeuda"),t); 
		 Periodo periodo = new Periodo("21/04/2010", "21/07/2017");
		 Condicion conpro = new FiltroSegunEcuacion(pro, 50.0, ">", periodo) ; 
		 meto.agregarCondicion(conpro);
	  ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia(); 	  	  
	  assertEquals(empresas.size(),1);
	  assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico"); }

	
	 /* @Test public void noHayEmpresasQueCumplan() throws
	  NoSeEncuentraLaEmpresaException,
	  NoSeEncuentraLaCuentaException,NoSeEncuentraLaCuentaEnElPeriodoException,
	  NoSeEncuentraElIndicadorException, ScriptException,
	  NoSePudoOrdenarLaCondicionException {
	  thrown.expect(NoHayEmpresasQueCumplanLaCondicionException.class);
	  Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
	  LocalDate tiempo1 = LocalDate.of(2016, 3, 12); LocalDate tiempo2 =
	  LocalDate.of(2015, 2, 12); Periodo periodosum = new
	  Periodo(tiempo1,tiempo2); Condicion cond2 = new
	  FiltroYOrdena(sum,700.0,">",periodosum); meto.agregarCondicion(cond2);
	  meto.aplicarMetodologia(); }*/
	 
	/* 
	 * @Test public void pruebaLongevidadyCreciente()throws
	 * NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
	 * ScriptException, NoSePudoOrdenarLaCondicionException,
	 * NoSeEncuentraLaCuentaException,
	 * NoSeEncuentraLaCuentaEnElPeriodoException{ Longevidad lon = new
	 * Longevidad(t.buscarIndicador("i_IngresoNeto"),t); Creciente cre = new
	 * Creciente(t.buscarIndicador("i_NivelDeuda"),t); LocalDate tiempo1 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo2 = LocalDate.of(2015, 2, 12);
	 * Periodo periodolon = new Periodo(tiempo1,tiempo2); LocalDate tiempo3 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo4 = LocalDate.of(2015, 2, 12);
	 * Periodo periodocre = new Periodo(tiempo3,tiempo4); Condicion condlon =
	 * new FiltroYOrdena(lon,3.0,">",periodolon); Condicion condcre = new
	 * FiltraYOrdenaAplicandoCriterioOrdenamiento(cre,periodocre,"mayorAmenor");
	 * meto.agregarCondicion(condlon); meto.agregarCondicion(condcre);
	 * ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
	 * assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico"); }
	 * 
	 * @Test public void pruebaSumtoriayDecreciente() throws
	 * NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
	 * ScriptException, NoSePudoOrdenarLaCondicionException,
	 * NoSeEncuentraLaCuentaException,
	 * NoSeEncuentraLaCuentaEnElPeriodoException{ Decreciente decre= new
	 * Decreciente(t.buscarIndicador("i_NivelDeuda"),t); Sumatoria sum=new
	 * Sumatoria(t.buscarIndicador("i_NivelDeuda"),t); LocalDate tiempo1 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo2 = LocalDate.of(2015, 2, 12);
	 * Periodo periododecre = new Periodo(tiempo1,tiempo2); LocalDate tiempo3 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo4 = LocalDate.of(2015, 2, 12);
	 * Periodo periodosum = new Periodo(tiempo3,tiempo4); Condicion condi = new
	 * Filtro(decre,periododecre); Condicion condi2 = new
	 * FiltroYOrdena(sum,7.0,">",periodosum); meto.agregarCondicion(condi);
	 * meto.agregarCondicion(condi2); ArrayList<PuntajeEmpresa> empresas =
	 * meto.aplicarMetodologia(); //Solo Facebook debe cumplir estas dos
	 * condiciones combinadas
	 * assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook"); }
	 * 
	 * 
	 * 
	 * @Test public void pruebaPromedioySumatoriaMayoraMenor() throws
	 * NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
	 * ScriptException, NoSePudoOrdenarLaCondicionException,
	 * NoSeEncuentraLaCuentaException,
	 * NoSeEncuentraLaCuentaEnElPeriodoException{ Promedio prom = new
	 * Promedio(t.buscarIndicador("i_NivelDeuda"),t); Sumatoria sum=new
	 * Sumatoria(t.buscarIndicador("i_NivelDeuda"),t); LocalDate tiempo1 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo2 = LocalDate.of(2015, 2, 12);
	 * Periodo periodoprom = new Periodo(tiempo1,tiempo2); LocalDate tiempo3 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo4 = LocalDate.of(2015, 2, 12);
	 * Periodo periodosum = new Periodo(tiempo3,tiempo4); Condicion cond1 = new
	 * FiltraYOrdenaAplicandoCriterioOrdenamiento(prom,periodoprom,"mayorAmenor"
	 * ); Condicion cond2 = new FiltroYOrdena(sum,7.0,">",periodosum);
	 * meto.agregarCondicion(cond1); meto.agregarCondicion(cond2);
	 * ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia(); //El que
	 * tiene mayor promedio del indicador de deuda es Facebook
	 * assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
	 * assertEquals(empresas.get(1).getNombreEmpresa(),"Pepsico"); //Esas dos
	 * empresas son las unicas cuya sumatoria de Nivel de Deuda sea mayor que 7,
	 * en el periodo de 2 años }
	 * 
	 * @Test public void pruebaPromedioySumatoriaMenoraMayor() throws
	 * NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
	 * ScriptException, NoSePudoOrdenarLaCondicionException,
	 * NoSeEncuentraLaCuentaException,
	 * NoSeEncuentraLaCuentaEnElPeriodoException{ Promedio prom = new
	 * Promedio(t.buscarIndicador("i_NivelDeuda"),t); Sumatoria sum=new
	 * Sumatoria(t.buscarIndicador("i_NivelDeuda"),t); LocalDate tiempo1 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo2 = LocalDate.of(2015, 2, 12);
	 * Periodo periodoprom = new Periodo(tiempo1,tiempo2); LocalDate tiempo3 =
	 * LocalDate.of(2016, 3, 12); LocalDate tiempo4 = LocalDate.of(2015, 2, 12);
	 * Periodo periodosum = new Periodo(tiempo3,tiempo4); Condicion cond1 = new
	 * FiltraYOrdenaAplicandoCriterioOrdenamiento(prom,periodoprom,"menorAmayor"
	 * ); Condicion cond2 = new FiltroYOrdena(sum,7.0,">",periodosum);
	 * meto.agregarCondicion(cond1); meto.agregarCondicion(cond2);
	 * ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia(); //El que
	 * tiene menor promedio del indicador de deuda es Pepsico
	 * assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico");
	 * assertEquals(empresas.get(1).getNombreEmpresa(),"Facebook"); }
	 */
	@After
	public void eliminarListas() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
		this.meto.getCondicionesDeMetodologia().clear();
		this.meto.getPuntajeEmpresas().clear();
	}

}
