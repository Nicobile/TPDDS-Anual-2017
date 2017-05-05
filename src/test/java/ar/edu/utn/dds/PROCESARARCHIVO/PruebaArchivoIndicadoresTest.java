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
    	   Procesador.cargarIndPredefinidos("1238*a");
    	   Procesador.cargarIndPredefinidos("1238*a");
    	   Procesador.cargarIndPredefinidos("c / d"); 
    	   Procesador.leerExcel();
       }
       
       
       @Test
       
       public void tamanioLista(){
    	   assertEquals(Procesador.getIndicadores().size(),5);
       }
       
       @Test
       
       public void verificarNodosLista(){
    	   assertEquals(Procesador.getIndicadores().get(2),"c / d");
       }
       
       @After
       
       public void eliminarLista(){
    	 this.Procesador.getIndicadores().clear();   
       }
       
}
