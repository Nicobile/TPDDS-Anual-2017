package ar.edu.utn.dds.PROCESARARCHIVO;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PruebaArchivoIndicadoresTest {
       private ProcesarIndicadores Procesador;
       
       @Before
       
       public void initIndicadores() throws FileNotFoundException, IOException{
    	   this.Procesador = new ProcesarIndicadores();
    	    
    	   Procesador.leerExcel();
       }
       
       
       @Test
       
       public void tamanioLista(){
    	   assertEquals(Procesador.getIndicadores().size(),4);
       }
       
       @Test
       
       public void verificarNodosLista(){
    	   assertEquals(Procesador.getIndicadores().get(1).getNombre(),"Chau");
    	   assertEquals(Procesador.getIndicadores().get(1).getOperacion(),"563+2,5");
    	   assertEquals(Procesador.getIndicadores().get(0).getNombre(),"Hola");
    	   assertEquals(Procesador.getIndicadores().get(3).getOperacion(),"650,9-9*9"); 
       }
       
       @After
       
       public void eliminarLista(){
    	 this.Procesador.getIndicadores().clear();   
       }
       
}
