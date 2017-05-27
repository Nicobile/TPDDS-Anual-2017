package ar.edu.utn.dds.procesarArchivos;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import excepciones.NoSeEncuentraEnLaLista;

public class PruebaArchivoTest {

	private LectorArchivo lector;
	private static final double DELTA = 1e-15;

	@Before

	public void initLectura() throws FileNotFoundException, IOException, NoSeEncuentraEnLaLista {
		this.lector = new LectorArchivo();
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

	}

	Cuenta cuenta = new Cuenta("c_cuentaC ", 1, "2013");
	Cuenta cuentaX = new Cuenta("cuentaB", 452369, "15/04/2010");
	Cuenta c = new Cuenta("cuentaA", 452362, "2015");
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test

	// prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException {
		assertFalse((this.lector.getLineasArchivo().isEmpty()));
	}

	@Test

	public void listaCantElementos() throws FileNotFoundException, IOException {
		assertEquals((this.lector.getEmpresas().size()), 4);
	}

	@Test

	// prueba para verficiar el valor de los elementos
	public void verificaContenidoListaEmpresas() throws FileNotFoundException, IOException {
		assertEquals(this.lector.getEmpresas().get(2).getNombre(), "Pepsico");
	}

	@Test

	public void verficarCuentasEmpresa() throws FileNotFoundException, IOException, NoSeEncuentraEnLaLista {
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getNombre(), "c_cuentaC");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getFecha(), "2013");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getValor(), 1,DELTA);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getNombre(), "c_cuentaC");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getFecha(), "2016");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getValor(), 0,DELTA);

		assertEquals(this.lector.getEmpresas().get(0).buscarUnaCuenta("c_cuentaA").getFecha(), c.getFecha());

		/*
		 * cuentaA no correspone a la empresa en la posicion 2 deberia retornar
		 * null
		 */
		assertNotNull(this.lector.obtenerEmpresa("Facebook").buscarUnaCuenta("c_cuentaA"));

		assertEquals(this.lector.consultarValorCuenta("CocaCola", "c_cuentaB", "2016"), 65,DELTA);
		assertEquals(this.lector.getEmpresas().get(2).buscarUnaCuentaPorFecha("c_cuentaC", "2013").getValor(), 1,DELTA);
	}

	@Test

	public void verificarCantidadCuentasEmpresa() throws FileNotFoundException, IOException {
		assertEquals(this.lector.getEmpresas().get(0).getCuentas().size(), 4);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().size(), 2);
		assertEquals(this.lector.getEmpresas().get(1).getCuentas().size(), 1);
		assertEquals(this.lector.getEmpresas().get(3).getCuentas().size(), 3);

	}

	@Test

	public void noSeEncuentraCuenta() throws NoSeEncuentraEnLaLista {
		thrown.expect(NoSeEncuentraEnLaLista.class);
		this.lector.obtenerEmpresa("Pepsico").buscarUnaCuenta("c_cuentaA");
	}

	@After

	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.lector.getEmpresas().clear();
	}

}
