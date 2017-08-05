package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Operando;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;


public class PruebaIndicadoresTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private Periodo p;
	
	private Periodo periodoSinCuentas;
	private static final double DELTA = 1e-15;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		
		p=new Periodo(LocalDate.of(2013,04, 21),LocalDate.of(2018,04, 21) );

		periodoSinCuentas = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2011, 04, 21)); 		
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void calcular() throws IndexOutOfBoundsException, NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		assertEquals(t.getIndicadores().get(1).getNombre(), "i_NivelDeuda");		
		assertEquals(t.calcular("Facebook", p, "i_NivelDeuda"), 0.12203366530588197, DELTA);
		
		

	}

	

	@Test
	public void indicadorRecursivoDosVeces() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
	assertEquals(t.calcular("Pepsico", p, "i_ROE"),12.740526524223508, DELTA);

	}

	@Test
	public void listarIndicadoresyCuentas() throws NoSeEncuentraLaEmpresaException {
// facebook tiene 22 cuentas y hasy 7 indicadores
		assertEquals(t.listarIndicadoresyCuentas(t.obtenerEmpresa("Facebook")).size(),29);
	}

	@Test
	public void noSeEncuentraIndicador() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", p, "i_IndicadorH");
	}

	@Test
	public void noSeEncuentraCuentaParaLaEmpresa() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraLaCuentaException.class);
	// pepsico no tiene en sus cuentas las necesarias para calcular ese indicador
		t.calcular("Pepsico", p, "i_Solvencia");
	}

	@Test
	public void ExpresionIncorrecta() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(IllegalArgumentException.class);

		t.calcular("Facebook", p, "i_IndicadorD");

	}

	@Test

	public void errorIndicadorMalEscrito() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", p, "j_ROE");
	}

	@Test

	public void errorCualquierCosaComoIndicador() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", p, "abjshdjkasdlksdaj");
	}

	@Test
	public void pruebaParser() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {
		Operando nodo = t.getParser().parse("c_Ventas + c_Utilidades", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		assertEquals(nodo.calcular(CocaCola, p), 404734, DELTA);
	}

	@Test
	public void pruebaDividirPor0() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(ArithmeticException.class);
		Operando nodo = t.getParser().parse("(c_Ventas + c_Utilidades)/0", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		nodo.calcular(CocaCola, p);
	}

	@Test
	public void parsearCualquierCosa() {
		thrown.expect(IllegalArgumentException.class);
		t.getParser().parse("sadfdsfsfasdasdfdscsacsad", t.getIndicadores());
	}

	@Test
	public void parsearCualquierIndicadorDesdeArchivoIndicadores() {
		thrown.expect(IllegalArgumentException.class);
		t.getParser().parse("j_IndicadorF", t.getIndicadores());
	}
	@Test
	public void NoSeEncuentraLaCuentaEnElPeriodo() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException{
		thrown.expect(NoSeEncuentraLaCuentaEnElPeriodoException.class);
		
			t.calcular("Pepsico", periodoSinCuentas, "i_ROE");
	}

	@After
	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
	}

}
