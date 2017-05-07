package ar.edu.utn.dds.PROCESARARCHIVO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PruebaIndicadoresTest {
	    private LeerArchivo lector;
        private ProcesarIndicadores procesador1;
        
        @Before
        public void inicializacion() throws FileNotFoundException, IOException{
        	this.lector = new LeerArchivo();
        	this.procesador1 = new ProcesarIndicadores();
        	this.lector.leerArchivo();
        	this.procesador1.leerExcel();
        /*	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(0));
        	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(1));
        	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(2)); */
        }
        
        
        
        @Test
        public void calcular(){
             assertEquals(this.procesador1.calcularIndicador(this.procesador1.getIndicadores().get(0)),1400);
        }
        
        
        
}
