package tp;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class pruebaArchivo {
	
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
	}


    @After
    
    public void eliminarLista(){
    	this.lector.getLineasArchivo().clear();
    	this.lector.getEmpresas().clear();
    }
	
}
