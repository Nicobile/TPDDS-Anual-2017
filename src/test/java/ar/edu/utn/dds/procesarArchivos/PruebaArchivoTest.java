package ar.edu.utn.dds.procesarArchivos;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;

public class PruebaArchivoTest {

	private LectorArchivo lector;
	private Traductor t;
	private static final double DELTA = 1e-15;

	@Before

	public void initLectura() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresa {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test

	// prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException {
		assertFalse((this.lector.getLineasArchivo().isEmpty()));
	}

	@Test

	public void verficarCuentasEmpresa() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresa,
			NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {

		assertEquals(t.obtenerEmpresa("Facebook").getCuentas().size(), 79);
		assertEquals(t.consultarValorCuenta("CocaCola", "c_IngresoNetoEnOperacionesDiscontinuadas", "2015"), 452,
				DELTA);
		assertEquals(t.obtenerEmpresa("Twitter").buscarUnaCuentaPorFecha("c_IngresoNetoEnOperacionesContinuas", "2015")
				.getValor(), 13453, DELTA);
	}

	@Test

	public void noSeEncuentraCuenta() throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta {
		thrown.expect(NoSeEncuentraLaCuenta.class);
		t.obtenerEmpresa("Pepsico").buscarUnaCuenta("c_cuentaA");
	}

	@Test

	public void noSeEncuentraCuentaEnUnaFecha()
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		thrown.expect(NoSeEncuentraLaCuentaEnEsaFecha.class);
		this.t.obtenerEmpresa("Pepsico").buscarUnaCuentaPorFecha("c_IngresoNetoEnOperacionesContinuas", "2003");
	}

	@After

	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
	}

}
