package ar.edu.utn.dds.PROCESARARCHIVO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
        	this.procesador1 = new ProcesarIndicadores(lector);
        	this.lector.leerArchivo();
        	this.procesador1.leerExcel();
        /*	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(0));
        	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(1));
        	this.procesador1.buscarEmpresa(this.procesador1.getIndicadores().get(2)); */
        }
        
        
        
        @Test
        public void calcular() throws ParseException{
           assertEquals(this.procesador1.calcularIndicador(this.procesador1.getIndicadores().get(0),lector),1400);
        	assertEquals(this.procesador1.getIndicadores().size(),3);
        	assertEquals(this.procesador1.getIndicadores().get(0).getNombre(),"Indicador1");
        	assertEquals(this.procesador1.getIndicadores().get(0).getNombreEmpresa(),"Facebook");
        	assertEquals(this.procesador1.getIndicadores().get(0).getOperacion(),"cuentaA+cuentaD");
        	assertEquals(this.procesador1.getIndicadores().get(1).getNombre(),"Indicador2");
        	assertEquals(this.procesador1.getIndicadores().get(1).getNombreEmpresa(),"Facebook");
        	assertEquals(this.procesador1.getIndicadores().get(1).getOperacion(),"cuentaB*(cuentaC+cuentaE)");
        	assertEquals(this.procesador1.getIndicadores().get(2).getNombre(),"Indicador3");
        	assertEquals(this.procesador1.getIndicadores().get(2).getNombreEmpresa(),"Facebook");
        	assertEquals(this.procesador1.getIndicadores().get(2).getOperacion(),"cuentaB-cuentaC");
        	
        	//assertEquals(this.procesador1.descomponerString("4+5+cuentaA", this.procesador1.obtenerSoloLasCuentasMasRecientesDeEmpresa(this.lector.getEmpresas().get(0))),"4+5+1000");
        	
        	
        }
        
        
        
}
