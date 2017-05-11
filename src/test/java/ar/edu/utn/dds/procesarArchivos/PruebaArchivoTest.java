package ar.edu.utn.dds.procesarArchivos;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import junit.framework.Assert;

public class PruebaArchivoTest {

	private LectorArchivo lector;

	@Before

	public void initLectura() throws FileNotFoundException, IOException {
		this.lector = new LectorArchivo();
		this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());

	}

	Cuenta cuenta = new Cuenta("cuentaB", 65, "11/02/2011");
	Cuenta cuentaX = new Cuenta("cuentaB", 452369, "15/04/2010");
	Cuenta c = new Cuenta("cuentaA", 452362, "27/03/2015");

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
		assertEquals(this.lector.getEmpresas().get(2).getNombre(), "CocaCola");
	}

	@Test

	public void verficarCuentasEmpresa() throws FileNotFoundException, IOException {
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getNombre(), "cuentaB");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getFecha(), "11/02/2011");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getValor(), 65);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getNombre(), "cuentaC");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getFecha(), "11/02/2011");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getValor(), 6);

		assertEquals(this.lector.getEmpresas().get(2).buscarUnaCuenta("cuentaB").getNombre(), cuenta.getNombre());
		assertEquals(this.lector.getEmpresas().get(2).buscarUnaCuenta("cuentaB").getFecha(), cuenta.getFecha());
		assertEquals(this.lector.getEmpresas().get(2).buscarUnaCuenta("cuentaB").getValor(), cuenta.getValor());

		assertEquals(this.lector.getEmpresas().get(0).buscarUnaCuenta("cuentaA").getFecha(), c.getFecha());

		assertNull(this.lector.getEmpresas().get(2).buscarUnaCuenta("cuentaA"));/*
							 * cuentaA no correspone a la empresa en la posicion
							 * 2 deberia retornar null
							 */
		assertNotNull(this.lector.getEmpresas().get(0).buscarUnaCuenta("cuentaA"));

	}

	@Test

	public void verificarCantidadCuentasEmpresa() throws FileNotFoundException, IOException {
		assertEquals(this.lector.getEmpresas().get(0).getCuentas().size(), 1);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().size(), 2);
		assertEquals(this.lector.getEmpresas().get(1).getCuentas().size(), 1);
		assertEquals(this.lector.getEmpresas().get(3).getCuentas().size(), 2);

	}

	@After

	public void eliminarLista() {
		this.lector.getLineasArchivo().clear();
		this.lector.getEmpresas().clear();
	}

}
