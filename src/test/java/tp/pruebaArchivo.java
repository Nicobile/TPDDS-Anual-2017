package tp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	public void lecturArch() throws FileNotFoundException, IOException{
		this.persona.leerArchivo();
		assertTrue((this.persona.getLineasArchivo().size())>0);
	}
	
	
}
