package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import excepciones.NoSeEncuentraEnLaLista;

public class PruebaIndicadoresTest {
	private LectorArchivo lector;
	private ProcesarIndicadores procesador1;
	private static final double DELTA = 1e-15;

	@Before
	public void inicializacion() throws FileNotFoundException, IOException, NoSeEncuentraEnLaLista {
		this.lector = new LectorArchivo();

		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
		this.procesador1 = new ProcesarIndicadores();
		this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void calcular() throws IndexOutOfBoundsException, NoSeEncuentraEnLaLista {

		assertEquals(this.procesador1.indicadores.get(1).getNombre(), "i_IndicadorB");
		assertEquals(procesador1.calcular((lector.obtenerEmpresa("CocaCola")), "2016", "i_IndicadorB"), 390, DELTA);

	}

	@Test
	public void calcular2() throws NoSeEncuentraEnLaLista {
		assertEquals(procesador1.calcular(lector.getEmpresas().get(0), "2015", "i_IndicadorA"), 2481480, DELTA);
		assertEquals(procesador1.calcular(lector.obtenerEmpresa("CocaCola"), "2016", "i_IndicadorC"), 2346, DELTA);

	}

	@Test
	public void listarIndicadoresyCuentas() throws NoSeEncuentraEnLaLista {

		assertTrue(procesador1.listarIndicadodoresyCuentas(lector.obtenerEmpresa("Facebook"), "2015").get(0)
				.equals("i_IndicadorA"));
	}

	@Test
	public void noSeEncuentraIndicador() throws NoSeEncuentraEnLaLista {
		thrown.expect(NoSeEncuentraEnLaLista.class);
		procesador1.calcular(lector.obtenerEmpresa("CocaCola"), "2017", "i_IndicadorA");
	}

	@Test
	public void ExpresionIncorrecta() throws NoSeEncuentraEnLaLista {
		thrown.expect(IllegalArgumentException.class);

		procesador1.calcular(lector.obtenerEmpresa("Facebook"), "2015", "i_IndicadorD");

	}

	@Test

	public void calcularConParentesis() throws NoSeEncuentraEnLaLista {
		assertEquals(procesador1.calcular(lector.obtenerEmpresa("CocaCola"), "2016", "i_IndicadorE"), 2376, DELTA);
	}

	@After
	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.lector.getEmpresas().clear();
		this.procesador1.getIndicadores().clear();
	}

}
