package tp;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class pruebaArchivo {
	
    private Persona persona;
	
	@Before
	
	public void initPerson() throws FileNotFoundException, IOException{
		this.persona = new Persona();
		this.persona.leerArchivo();
	}
	
	
	@Test
	
	//prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException{
		assertFalse((this.persona.getLineasArchivo().isEmpty()));
	}
	
	@Test
	
	public void listaCantElementos() throws FileNotFoundException, IOException{
		assertEquals((this.persona.getLineasArchivo().size()),5);
	}
	
	@Test
	
	//prueba para verficiar el valor de los elementos
	public void verificaContenidoLista() throws FileNotFoundException, IOException{
		assertEquals(this.persona.getLineasArchivo().get(0).getNombreEmpresa(), "Facebook");
		assertEquals(this.persona.getLineasArchivo().get(0).getNombreCuenta(), "cuentaA");
		assertEquals(this.persona.getLineasArchivo().get(0).getValorCuenta(), 452362);
		assertEquals(this.persona.getLineasArchivo().get(1).getNombreEmpresa(), "Twitter");
		assertEquals(this.persona.getLineasArchivo().get(1).getNombreCuenta(), "cuentaB");
		assertEquals(this.persona.getLineasArchivo().get(1).getValorCuenta(), 452369);
		assertEquals(this.persona.getLineasArchivo().get(2).getNombreEmpresa(), "CocaCola");
		assertEquals(this.persona.getLineasArchivo().get(2).getNombreCuenta(), "cuentaB");
		assertEquals(this.persona.getLineasArchivo().get(2).getValorCuenta(), 654263);
		assertEquals(this.persona.getLineasArchivo().get(3).getNombreEmpresa(), "Pepsico");
		assertEquals(this.persona.getLineasArchivo().get(3).getNombreCuenta(), "cuentaC");
		assertEquals(this.persona.getLineasArchivo().get(3).getValorCuenta(), 5623521);
	}

    @After
    
    public void eliminarLista(){
    	this.persona.getLineasArchivo().clear();
    }
	
}
