package ar.edu.utn.dds.pruebaMetodologias;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicion;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class WarrenBuffetTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Metodologia meto;
	
	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresa, NoSeEncuentraElIndicador, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.meto = new Metodologia("WarrenBufet");
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
		Creciente cre= new Creciente(t.buscarIndicador("i_ROE"),t);
		Condicion cond1 = new Condicion(cre,10,"mayor");
		Sumatoria sum = new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond2 = new Condicion(sum,1,"menor");
		Creciente cre2 = new Creciente(t.buscarIndicador("i_MargenVentas"),t);
		Condicion cond3 = new Condicion(cre2,10,"mayor");
		Longevidad lon = new Longevidad(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond4 = new Condicion(lon,10.0,">",10);
		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		meto.agregarCondicion(cond3);
		meto.agregarCondicion(cond4);
	}
	
	
	@Test 
	public void pruebaHayEmpresasQueCumplen() throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		assertTrue(empresas.size() > 0);
	}
	
	@Test
	public void seDebeInvertirEnFacebook() throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
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
