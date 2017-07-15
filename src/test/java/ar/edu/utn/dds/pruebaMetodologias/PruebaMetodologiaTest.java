package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicion;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicion;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
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
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresa, NoSeEncuentraElIndicador {
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
	public void pruebaSoloCondicionDecreciente() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Decreciente decre= new Decreciente(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond = new Condicion(decre,2);	
		meto.agregarCondicion(cond);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		//Solo Twitter, Cocacola y Facebook cumplen con la condidion de que su deuda sea decreciente
		assertEquals(empresas.size(),3);
		assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
		assertEquals(empresas.get(1).getNombreEmpresa(),"CocaCola");
		assertEquals(empresas.get(2).getNombreEmpresa(),"Twitter");
	}
	
	
	
	@Test     
	public void pruebaSoloCondicionSumatoria() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond2 = new Condicion(sum,7.0,">",2);		
		meto.agregarCondicion(cond2);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		//Solo Pepsi y Facebook cumplen con la condicion
		assertEquals(empresas.get(1).getNombreEmpresa(),"Pepsico");
		assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
	

	}
	
	@Test
	public void pruebaSoloCondicionLongevidad() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Longevidad lon = new Longevidad(t.buscarIndicador("i_IngresoNeto"),t);
		Condicion condlon = new Condicion(lon,3.0,">",3);	
		meto.agregarCondicion(condlon);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		/*Todas las empresas tienen mas de 3 años"*/
		assertEquals(empresas.size(),4);
	}
	
	@Test
	public void pruebaSoloCondicionCreciente()throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Creciente cre = new Creciente(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion concre = new Condicion(cre,2,"mayor");
		meto.agregarCondicion(concre);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		/*PEPSI incremento su deuda en 123,69 */
		assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico");
	}
	
	@Test
	public void pruebaSoloPromedio()throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Promedio pro = new Promedio(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion conpro = new Condicion (pro,200.0,">",2);
		meto.agregarCondicion(conpro);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		/*Solo facebook tiene un promedio de deuda mayor a 200. Su promedio es de 282*/
		assertEquals(empresas.size(),1);
		assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
	}
	
	@Test
	public void noHayEmpresasQueCumplan() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador, ScriptException, NoSePudoOrdenarLaCondicion {
		thrown.expect(NoHayEmpresasQueCumplanLaCondicion.class);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond2 = new Condicion(sum,700.0,">",2);		
		meto.agregarCondicion(cond2);
		meto.aplicarMetodologia();
	}
	
	
	@Test
	public void pruebaLongevidadyCreciente()throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Longevidad lon = new Longevidad(t.buscarIndicador("i_IngresoNeto"),t);
		Creciente cre = new Creciente(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion condlon = new Condicion(lon,3.0,">",3);
		Condicion condcre = new Condicion(cre,2,"mayor");
		meto.agregarCondicion(condlon);
		meto.agregarCondicion(condcre);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico");
	}

	@Test   
	public void pruebaSumtoriayDecreciente() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Decreciente decre= new Decreciente(t.buscarIndicador("i_NivelDeuda"),t);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion condi = new Condicion(decre,2);
		Condicion condi2 = new Condicion(sum,7.0,">",2);
		meto.agregarCondicion(condi);		
		meto.agregarCondicion(condi2);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		//Solo Facebook debe cumplir estas dos condiciones combinadas
		assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
	}
	
	
	
	@Test   
	public void pruebaPromedioySumatoriaMayoraMenor() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"),t);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond1 = new Condicion(prom,2,"mayor");
		Condicion cond2 = new Condicion(sum,7.0,">",2);		
		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		//El que tiene mayor promedio del indicador de deuda es Facebook
		assertEquals(empresas.get(0).getNombreEmpresa(),"Facebook");
		assertEquals(empresas.get(1).getNombreEmpresa(),"Pepsico");
		//Esas dos empresas son las unicas cuya sumatoria de Nivel de Deuda sea mayor que 7, en el periodo de 2 años
	}
	
	@Test   
	public void pruebaPromedioySumatoriaMenoraMayor() throws NoSeEncuentraElIndicador, NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha{
		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"),t);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond1 = new Condicion(prom,2,"menor");
		Condicion cond2 = new Condicion(sum,7.0,">",2);		
		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		//El que tiene menor promedio del indicador de deuda es Pepsico
		assertEquals(empresas.get(0).getNombreEmpresa(),"Pepsico");
		assertEquals(empresas.get(1).getNombreEmpresa(),"Facebook");
	} 
	
	@After
	public void eliminarListas() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
		this.meto.getCondicionesDeMetodologia().clear();
		this.meto.getPuntajeEmpresas().clear();
	}
	
	
}
