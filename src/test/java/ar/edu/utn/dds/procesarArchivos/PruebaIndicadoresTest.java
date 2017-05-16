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
        	this.procesador1.inciarIndicadoresDeEmpresa(lector.getEmpresas().get(0));
        	this.procesador1.inciarIndicadoresDeEmpresa(lector.getEmpresas().get(1));
        	this.procesador1.inciarIndicadoresDeEmpresa(lector.getEmpresas().get(2));
        	this.procesador1.inciarIndicadoresDeEmpresa(lector.getEmpresas().get(3));
        }
        
        
        
        @Test
        public void calcular() throws IndexOutOfBoundsException{
            //assertEquals(this.lector.getEmpresas().size(),4);
        	assertEquals(this.lector.getEmpresas().get(0).calcularIndicador("i_IndicadorA"),45363);
        	//assertEquals(this.lector.getEmpresas().get(0).getIndicadores().size(),1);
        	//assertEquals(this.procesador1.getIndicadores().size(),3);        	        	
        }
        @After
        	 public void eliminarLista(){
        	    	this.lector.getLineasArchivo().clear();
        	    	this.lector.getEmpresas().clear();
        	    	this.procesador1.getIndicadores().clear();
        }
        
        
        
}
