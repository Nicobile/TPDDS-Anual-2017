package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
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
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresa {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores(t);
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void calcular() throws IndexOutOfBoundsException, NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {

		assertEquals(t.getIndicadores().get(1).getNombre(), "i_IndicadorB");
		assertEquals(t.calcular("CocaCola", "2016", "i_IndicadorB"), 390, DELTA);

	}

	@Test
	public void calcular2() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador {
		assertEquals(t.calcular(t.getEmpresas().get(0).getNombre(), "2015", "i_IndicadorA"), 2481480, DELTA);
		assertEquals(t.calcular("CocaCola", "2016", "i_IndicadorC"), 2346, DELTA);

	}

	@Test
	public void listarIndicadoresyCuentas() throws NoSeEncuentraLaEmpresa {

		assertTrue(t.listarIndicadodoresyCuentas(t.obtenerEmpresa("Facebook"), "2015").get(0).equals("i_IndicadorA"));
	}

	@Test
	public void noSeEncuentraIndicador() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		thrown.expect(NoSeEncuentraElIndicador.class);
		t.calcular("CocaCola", "2017", "i_IndicadorH");
	}

	@Test
	public void noSeEncuentraCuentaParaLaEmpresa() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		thrown.expect(NoSeEncuentraLaCuenta.class);
		t.calcular("Pepsico", "2016", "i_IndicadorA");
	}

	@Test
	public void ExpresionIncorrecta() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		thrown.expect(IllegalArgumentException.class);

		t.calcular("Facebook", "2015", "i_IndicadorD");

	}

	@Test

	public void calcularConParentesis() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		assertEquals(t.calcular("CocaCola", "2016", "i_IndicadorE"), 2376, DELTA);
	}
	
	
	@Test
	public void pruebaParser() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
	NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		Operando nodo = t.getParser().parse("c_cuentaA + c_cuentaB", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		assertEquals(nodo.calcular(CocaCola, "2016"),71,DELTA);
	}
	
	@Test
	public void pruebaDividirPor0() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta,
	NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		thrown.expect(ArithmeticException.class);
		Operando nodo = t.getParser().parse("(c_cuentaA + c_cuentaB)/0", t.getIndicadores());
		Empresa CocaCola = t.obtenerEmpresa("CocaCola");
		nodo.calcular(CocaCola, "2016");
	}

	@After
	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
		this.t.getIndicadores().clear();
	}

}
