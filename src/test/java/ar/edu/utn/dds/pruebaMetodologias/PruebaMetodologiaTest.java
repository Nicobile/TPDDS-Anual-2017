package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.script.ScriptException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.modelo.ValorCalculable;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class PruebaMetodologiaTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia meto;
	private Periodo periodo;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraElIndicadorException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.meto = new Metodologia("Metod");
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

		periodo = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2017, 07, 21));

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void pruebaSoloCondicionDecreciente() throws NoSeEncuentraElIndicadorException,
			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		EntityManager em = Utilidades.getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		Indicador ind = t.buscarIndicador("i_NivelDeuda");
		//em.persist(ind);
		ValorCalculable decre = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);
		
		Condicion cond = new Filtro(decre, 4);
		em.persist(cond);
		em.persist(decre);
		meto.agregarCondicion(cond);
		//em.persist(meto);
		et.commit();
		em.close();
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();

		/* Solo Pepsico cumplen con la condidion de que su deuda sea decreciente */
		assertEquals(empresas.size(), 1);
		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));

	}

//	@Test
//	public void pruebaSoloCondicionCreciente() throws NoSeEncuentraElIndicadorException,
//			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
//
//		Creciente creciente = new Creciente(t.buscarIndicador("i_NivelDeuda"), t);
//		Condicion cond = new Filtro(creciente, 4);
//
//		meto.agregarCondicion(cond);
//
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/*
//		 * Solo Twitter, Cocacola y Facebook cumplen con la condidion de que su deuda
//		 * sea decreciente
//		 */
//
//		assertEquals(empresas.size(), 3);
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Facebook"), empresas.get(0));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "CocaCola"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(2));
//
//	}
//
//	@Test
//	public void pruebaSoloCondicionSumatoria() throws NoSeEncuentraElIndicadorException,
//			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
//
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 400, ">", periodo);
//		meto.agregarCondicion(cond2);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		assertEquals(empresas.size(), 1);
//
//	}
//
//	@Test
//	public void pruebaSoloCondicionLongevidad() throws NoSeEncuentraElIndicadorException,
//			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
//		Longevidad lon = new Longevidad(t);
//
//		Filtro condlon = new Filtro(lon, 20);
//		meto.agregarCondicion(condlon);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/*
//		 * Solo CocacolaYpesico tienen mas de 20"
//		 */
//
//		assertEquals(empresas.size(), 2);
//	}
//
//	@Test
//	public void pruebaSoloPromedio() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		Promedio pro = new Promedio(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion conpro = new FiltroSegunEcuacion(pro, 50, ">", periodo);
//		meto.agregarCondicion(conpro);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		assertEquals(empresas.size(), 1);
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
//	}
//	@Test
//	public void pruebaSoloMediana() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//	ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//	NoSeEncuentraLaCuentaEnElPeriodoException {
//
//Mediana mediana= new Mediana(t.buscarIndicador("i_NivelDeuda"), t);
//Condicion conMediana = new FiltroSegunEcuacion(mediana, 50, ">", periodo);
//meto.agregarCondicion(conMediana);
//ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//assertEquals(empresas.size(), 1);
//assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Facebook"), empresas.get(0));
//
//
//}
//	
//
//	@Test
//	public void noHayEmpresasQueCumplan() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException, ScriptException,
//			NoSePudoOrdenarLaCondicionException {
//
//		thrown.expect(NoHayEmpresasQueCumplanLaCondicionException.class);
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 500, ">", periodo);
//		Condicion cond = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, "mayorAmenor");
//		meto.agregarCondicion(cond2);
//		meto.agregarCondicion(cond);
//		meto.aplicarMetodologia();
//
//	}
//
//	@Test
//	public void sumaYOrdenoMayorAmenor() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException, ScriptException,
//			NoSePudoOrdenarLaCondicionException {
//
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 200, ">", periodo);
//		Condicion cond = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, "mayorAmenor");
//		meto.agregarCondicion(cond2);
//		meto.agregarCondicion(cond);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "CocaCola"), empresas.get(2));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Facebook"), empresas.get(3));
//
//	}
//
//	@Test
//	public void sumaYOrdenoMayorAMenorProbandoSiFiltra() throws NoSeEncuentraLaEmpresaException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
//			NoSeEncuentraElIndicadorException, ScriptException, NoSePudoOrdenarLaCondicionException {
//
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 348, ">", periodo);
//		Condicion cond = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, "mayorAmenor");
//		meto.agregarCondicion(cond2);
//		meto.agregarCondicion(cond);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/* solo 3 empresas cumplen con la condicion de suma */
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "CocaCola"), empresas.get(2));
//
//	}
//
//	@Test
//	public void sumaYOrdenoMenorAmayorProbandoSiFiltra() throws NoSeEncuentraLaEmpresaException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
//			NoSeEncuentraElIndicadorException, ScriptException, NoSePudoOrdenarLaCondicionException {
//
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 349, ">", periodo);
//		Condicion cond = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, "menorAmayor");
//		meto.agregarCondicion(cond2);
//		meto.agregarCondicion(cond);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/* solo 3 empresas cumplen con la condicion de suma */
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(0));
//
//	}
//
//	@Test
//	public void pruebaLongevidadyCreciente() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		Longevidad lon = new Longevidad(t);
//		Creciente cre = new Creciente(t.buscarIndicador("i_NivelDeuda"), t);
//		/*
//		 * creo un nuevo constructor que se debe usar para longevidad, creciente y
//		 * decreciente ya que no usan periodos
//		 */
//
//		/* longevidad no usa el periodo */
//		Condicion condlon = new Filtro(lon, 10);
//
//		/*
//		 * no usa periodo, solo se fijaque en la diferencia de de años(el
//		 * ultimoparametro de Filtro) y la fecha de hoy haya crecido el indicador
//		 */
//		Condicion condcre = new Filtro(cre, 5);
//		meto.agregarCondicion(condlon);
//		meto.agregarCondicion(condcre);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//		/*
//		 * estas condiciones no suman puntaje solo filtran por eso no estaria bien
//		 * preguntar el orden
//		 */
//		assertEquals(empresas.size(), 2);
//
//	}
//
//	@Test
//	public void pruebaSumtoriayDecreciente() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		Decreciente decre = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//		Condicion condi = new Filtro(decre, 10);
//		Condicion condi2 = new FiltroSegunEcuacion(sum, 400, ">", periodo);
//		meto.agregarCondicion(condi);
//		meto.agregarCondicion(condi2);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//		/*
//		 * ninguna de las condiciones suman puntaje, pero solo una empresa cumple abas
//		 * condiciones
//		 */
//		assertEquals(empresas.size(), 1);
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
//
//	}
//
//	@Test
//	public void pruebaPromedioySumatoriaMenoraMayor() throws NoSeEncuentraElIndicadorException,
//			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
//
//		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"), t);
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(prom, periodo, "menorAmayor");
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 348, ">", periodo);
//		meto.agregarCondicion(cond1);
//		meto.agregarCondicion(cond2);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/*
//		 * solo 3 empresas cumplen cond coca pepsi y twitter, cumplen la condicion de
//		 * suma(que solo filtra) y el promedio se ordena de menor a mayor con lo cual el
//		 * orden deberia ser coca twitter pepsico,
//		 */
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "CocaCola"), empresas.get(0));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(2));
//
//	}
//
//	@Test
//	public void pruebaPromedioySumatoriaMayorAmenor() throws NoSeEncuentraElIndicadorException,
//			NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
//
//		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"), t);
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//
//		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(prom, periodo, "mayorAmenor");
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 348, ">", periodo);
//		meto.agregarCondicion(cond1);
//		meto.agregarCondicion(cond2);
//		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
//
//		/*
//		 * solo 3 empresas cumplen cond coca pepsi y twitter, cumplen la condicion de
//		 * suma(que solo filtra) y el promedio se ordena de mayor a menor con lo cual el
//		 * orden deberia ser pepsico twitter coca
//		 */
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "CocaCola"), empresas.get(2));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Twitter"), empresas.get(1));
//		assertEquals(t.buscarEmpresaEnPuntajeEmpresa(empresas, "Pepsico"), empresas.get(0));
//	}
//
//	public void sinCuentasEnPeriodo() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		thrown.expect(NoSeEncuentraLaCuentaEnElPeriodoException.class);
//
//		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"), t);
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"), t);
//		Periodo periodoSinCuentas = new Periodo(LocalDate.of(2000, 1, 20), LocalDate.of(2001, 1, 20));
//		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(prom, periodoSinCuentas, "mayorAmenor");
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 348, ">", periodoSinCuentas);
//
//		meto.agregarCondicion(cond1);
//		meto.agregarCondicion(cond2);
//		meto.aplicarMetodologia();
//	}
//
//	@Test
//	public void noSeEncuentraIndicador() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		thrown.expect(NoSeEncuentraElIndicadorException.class);
//		Promedio prom = new Promedio(t.buscarIndicador("i_Solvecia"), t);
//		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_Solvecia"), t);
//
//		Condicion cond1 = new OrdenaAplicandoCriterioOrdenamiento(prom, periodo, "mayorAmenor");
//		Condicion cond2 = new FiltroSegunEcuacion(sum, 348, ">", periodo);
//		meto.agregarCondicion(cond1);
//		meto.agregarCondicion(cond2);
//		meto.aplicarMetodologia();
//
//	}
//
//	/* pruebo que devuelva errores sobre otras condiciones */
//	@Test
//	public void noSeEncuentraIndicador2() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		thrown.expect(NoSeEncuentraElIndicadorException.class);
//		
//		Creciente cre = new Creciente(t.buscarIndicador("i_Solvecia"), t);
//		Condicion cond1 = new Filtro(cre, 2);
//		
//		meto.agregarCondicion(cond1);
//		meto.aplicarMetodologia();
//
//	}
//
//	public void sinCuentasEnPeriodo2() throws NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException,
//			ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
//			NoSeEncuentraLaCuentaEnElPeriodoException {
//		thrown.expect(NoSeEncuentraLaCuentaEnElPeriodoException.class);
//
//		Creciente cre = new Creciente(t.buscarIndicador("i_Solvecia"), t);
//		Condicion cond1 = new Filtro(cre, 1);
//
//		meto.agregarCondicion(cond1);
//		meto.aplicarMetodologia();
//
//	}
//	
//	public void PruebaCrecienteYDecrecienteMismosAnios() throws ScriptException, NoSePudoOrdenarLaCondicionException,
//			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
//			NoSeEncuentraElIndicadorException, NoSeEncuentraLaEmpresaException {
//		thrown.expect(NoHayEmpresasQueCumplanLaCondicionException.class);
//		
//		Creciente cre = new Creciente(t.buscarIndicador("i_ROE"), t);
//		Condicion cond1 = new Filtro(cre, 10);
//		Decreciente decre = new Decreciente(t.buscarIndicador("i_NivelDeuda"), t);
//		Condicion cond2 = new Filtro(decre, 10);
//		
//		meto.agregarCondicion(cond1);
//		meto.agregarCondicion(cond2);
//		meto.aplicarMetodologia();
//	}

	@After
	public void eliminarListas() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
		this.meto.getCondicionesDeMetodologia().clear();
		this.meto.getPuntajeEmpresas().clear();
	}
}
