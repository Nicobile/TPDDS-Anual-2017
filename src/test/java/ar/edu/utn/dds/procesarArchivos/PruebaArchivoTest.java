package ar.edu.utn.dds.procesarArchivos;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;

public class PruebaArchivoTest {

	private LectorArchivo lector;
	private Traductor t;
	private Periodo periodoSinCuentas;

	@Before

	public void initLectura() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {
		this.t = new Traductor();
		this.lector = new LectorArchivo(t);
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

		periodoSinCuentas = new Periodo(LocalDate.of(2010, 04, 21), LocalDate.of(2011, 04, 21));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test

	// prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException {
		assertFalse((this.lector.getLineasArchivo().isEmpty()));
	}

	@Test

	public void verficarCuentasEmpresa() throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		assertEquals(t.obtenerEmpresa("Facebook").getCuentas().size(), 22);
		assertEquals(t.obtenerEmpresa("Pepsico").getCuentas().size(), 23);
		assertEquals(t.obtenerEmpresa("Twitter").getCuentas().size(), 22);
		assertEquals(t.obtenerEmpresa("CocaCola").getCuentas().size(), 22);

	}

	@Test

	public void noSeEncuentraCuenta() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException {
		thrown.expect(NoSeEncuentraLaCuentaException.class);
		t.obtenerEmpresa("Pepsico").buscarUnaCuenta("c_cuentaA");
	}

	@Test

	public void noSeEncuentraCuentaEnUnaFecha() throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException {
		thrown.expect(NoSeEncuentraLaCuentaEnElPeriodoException.class);
		this.t.obtenerEmpresa("Pepsico").buscarUnaCuentaPorPeriodo("c_IngresoNetoEnOperacionesContinuas",
				periodoSinCuentas);
	}

	@After

	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.t.getEmpresas().clear();
	}

}
