package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class PruebaIndicadoresTest {
	    private LectorArchivo lector;
        private ProcesarIndicadores procesador1;
        
        @Before
        public void inicializacion() throws FileNotFoundException, IOException{
        	this.lector = new LectorArchivo();
        	
        	this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
        	this.procesador1 = new ProcesarIndicadores(lector);
        	this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
      
        }
        
        
        
        @Test
        public void calcular() throws ParseException{
           assertEquals(this.procesador1.calcularIndicador(this.procesador1.getIndicadores().get(0)),452362);      	
           assertEquals(this.procesador1.calcularIndicador(this.procesador1.getIndicadores().get(1)),390);
           assertEquals(this.procesador1.calcularIndicador(this.procesador1.getIndicadores().get(2)),0);
           
          	
        
        	
        	        	        	
        }
        @After
        	 public void eliminarLista(){
        	    	this.lector.getLineasArchivo().clear();
        	    	this.lector.getEmpresas().clear();
        	    	this.procesador1.getIndicadores().clear();
        }
        
        
        
}
