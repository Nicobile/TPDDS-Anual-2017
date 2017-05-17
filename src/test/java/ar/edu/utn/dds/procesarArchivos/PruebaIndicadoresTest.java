package ar.edu.utn.dds.procesarArchivos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

public class PruebaIndicadoresTest {
	    private LectorArchivo lector;
        private ProcesarIndicadores procesador1;
      
        @Before
        public void inicializacion() throws FileNotFoundException, IOException{
        	this.lector = new LectorArchivo();
        	
        	this.lector.leerArchivo(this.getClass().getResource("/Datos.txt").getFile());
        	this.procesador1 = new ProcesarIndicadores();
        	this.procesador1.leerExcel(this.getClass().getResource("/Indicadores.xls").getFile());
        	
        	
        }
        
        
        
        @Test
        public void calcular() throws IndexOutOfBoundsException{
            
        	assertEquals(this.procesador1.indicadores.get(1).getNombre(),"i_IndicadorB");
        	assertEquals(procesador1.calcular((lector.obtenerEmpresa("CocaCola")), "2016", "i_IndicadorB"),390);
        	
        	
        	
        }
        
        
        @Test
        public void calcular2(){
        	assertEquals(procesador1.calcular(lector.getEmpresas().get(0), "2015","i_IndicadorA"),2481480);
        	assertEquals(procesador1.calcular(lector.obtenerEmpresa("CocaCola"), "2016","i_IndicadorC"),2346);
        	
        	
        }
        /*
        @Test
        public void noCorrespondeFecha(){
        	assertNull(procesador1.calcular(lector.obtenerEmpresa("CocaCola"), "2015","i_IndicadorB"));
        }*/
        
        @After
        	 public void eliminarLista(){
        	    	this.lector.getLineasArchivo().clear();
        	    	this.lector.getEmpresas().clear();
        	    	this.procesador1.getIndicadores().clear();
        }
        
        
        
}
