package tp;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class pruebaArchivo {
	
    private Persona persona;
	
	@Before
	
	public void initPerson(){
		this.persona = new Persona();
	}

	@Test
	
	//prueba para verificar que la lista contenga elementos
	public void listaTieneElementos() throws FileNotFoundException, IOException{
		this.persona.leerArchivo();
		assertTrue((this.persona.getLineasArchivo().size())>0);
	}
	
	@Test
	
	//prueba para verficiar el valor de los elementos
	public void verificaContenidoLista() throws FileNotFoundException, IOException{
		this.persona.leerArchivo();
		assertEquals(this.persona.getLineasArchivo().get(0).getNombreEmpresa(), "Facebook");
		assertEquals(this.persona.getLineasArchivo().get(0).getNombreCuenta(), "cuentaA");
		assertEquals(this.persona.getLineasArchivo().get(0).getValorCuenta(), 452362);
		assertEquals(this.persona.getLineasArchivo().get(1).getNombreEmpresa(), "Twitter");
	}
	
	
}
