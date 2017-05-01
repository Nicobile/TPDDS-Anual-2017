package ar.edu.utn.dds.PROCESARARCHIVO;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PruebaArchivoTest {
	
		
	
    private LeerArchivo lector;
    
	
	@Before
	
	public void initLectura() throws FileNotFoundException, IOException{
		this.lector = new LeerArchivo();
		this.lector.leerArchivo();
	}
	
	
	@Test
	
	//prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException{
		assertFalse((this.lector.getLineasArchivo().isEmpty()));
	}
	
	@Test
	
	public void listaCantElementos() throws FileNotFoundException, IOException{
		assertEquals((this.lector.getEmpresas().size()),4);
	}
	
	@Test
	
	//prueba para verficiar el valor de los elementos
	public void verificaContenidoListaEmpresas() throws FileNotFoundException, IOException{
		assertEquals(this.lector.getEmpresas().get(2).getNombre(),"CocaCola");
	}
	
	@Test
	
	public void verficarCuentasEmpresa() throws FileNotFoundException, IOException{
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getNombre(),"cuentaB");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getFecha(),"11/02/2011");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(0).getValor(),654263);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getNombre(),"cuentaC");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getFecha(),"11/02/2011");
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().get(1).getValor(),653);

	}
	
	@Test
	
	public void verificarCantidadCuentasEmpresa()throws FileNotFoundException, IOException{
		assertEquals(this.lector.getEmpresas().get(0).getCuentas().size(),1);
		assertEquals(this.lector.getEmpresas().get(2).getCuentas().size(),2);
		assertEquals(this.lector.getEmpresas().get(1).getCuentas().size(),1);
		assertEquals(this.lector.getEmpresas().get(3).getCuentas().size(),2);
		
	}
	
	
    @After
    
    public void eliminarLista(){
    	this.lector.getLineasArchivo().clear();
    	this.lector.getEmpresas().clear();
    }
	
}
