package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;

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
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class PruebaIndicadoresTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private Traductor t;
	private static final double DELTA = 1e-15;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
/*
	@Test
	public void calcular() throws IndexOutOfBoundsException, NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		assertEquals(t.getIndicadores().get(1).getNombre(), "i_NivelDeuda");
		assertEquals(t.calcular("CocaCola", "2015", "i_IngresoNeto"), 6104, DELTA);

	}

	@Test
	public void calcular2() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {
		assertEquals(t.calcular("Twitter", "2014", "i_NivelDeuda"), 1.4390243902439024, DELTA);

	}

	@Test
	public void indicadorRecursivoDosVeces() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		assertEquals(t.calcular("Pepsico", "2015", "i_ROE"), -0.22781721384205855, DELTA);

	}

	@Test
	public void listarIndicadoresyCuentas() throws NoSeEncuentraLaEmpresaException {

		assertTrue(t.listarIndicadodoresyCuentas(t.obtenerEmpresa("Facebook"), "2015").get(0).equals("i_ROE"));
	}

	@Test
	public void noSeEncuentraIndicador() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", "2017", "i_IndicadorH");
	}

	@Test
	public void noSeEncuentraCuentaParaLaEmpresa() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraLaCuentaException.class);
		t.calcular("Pepsico", "2015", "i_Solvencia");
	}

	@Test
	public void ExpresionIncorrecta() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(IllegalArgumentException.class);

		t.calcular("Facebook", "2015", "i_IndicadorD");

	}

	@Test

	public void errorIndicadorMalEscrito() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", "2015", "j_ROE");
	}

	@Test

	public void errorCualquierCosaComoIndicador() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(NoSeEncuentraElIndicadorException.class);
		t.calcular("CocaCola", "2014", "abjshdjkasdlksdaj");
	}

	@Test
	public void pruebaParser() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {
		Operando nodo = t.getParser().parse("c_Ventas + c_Utilidades", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		assertEquals(nodo.calcular(CocaCola, "2015"), 101000, DELTA);
	}

	@Test
	public void pruebaDividirPor0() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		thrown.expect(ArithmeticException.class);
		Operando nodo = t.getParser().parse("(c_Ventas + c_Utilidades)/0", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		nodo.calcular(CocaCola, "2015");
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
	}*/

	@After
	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
	}

}
