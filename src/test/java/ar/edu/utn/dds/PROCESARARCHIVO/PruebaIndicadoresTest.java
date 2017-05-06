package ar.edu.utn.dds.PROCESARARCHIVO;

import java.util.ArrayList;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PruebaIndicadoresTest {
        private ProcesarIndicadores procesador1;
        private ProcesarIndicadores procesador2;
        private ProcesarIndicadores procesador3;
        private Empresa Malcorra;
        private Empresa Sancor;
        private Empresa Correa;
        private Cuenta cuenta1;
        private Cuenta cuenta2;
        private Cuenta cuenta3;
        private Cuenta cuenta4;
        private ArrayList<Cuenta> cuentasMalcorra;
        private ArrayList<Cuenta> cuentasSancor;
        private ArrayList<Cuenta> cuentasCorrea;
        
        @Before
        public void inicializacion(){
        	this.procesador1 = new ProcesarIndicadores();
        	this.procesador2 = new ProcesarIndicadores();
        	this.procesador3 = new ProcesarIndicadores();
        	this.cuentasMalcorra = new ArrayList<Cuenta>();
        	this.cuentasSancor = new ArrayList<Cuenta>();
        	this.cuentasCorrea = new ArrayList<Cuenta>();
        	this.cuenta1 = new Cuenta("Cuenta1",800,"10/09/2017");
        	this.cuenta2 = new Cuenta("Cuenta2",1000,"05/08/2017");
        	this.cuenta3 = new Cuenta("Cuenta3",200,"06/03/2017");
        	this.cuenta4 = new Cuenta("Cuenta4",700,"05/10/2016");
        	this.cuentasCorrea.add(this.cuenta1);
        	this.cuentasCorrea.add(this.cuenta3);
        	this.cuentasMalcorra.add(this.cuenta2);
        	this.cuentasMalcorra.add(this.cuenta4);
        	this.cuentasSancor.add(this.cuenta1);
        	this.cuentasSancor.add(this.cuenta2);
        	this.Malcorra = new Empresa("Malcorra",this.cuentasMalcorra);
        	this.Sancor = new Empresa("Sancor",this.cuentasSancor);
        	this.Correa = new Empresa("Correa",this.cuentasCorrea);
        	this.procesador1.cargarIndPredefinidos("Indicador1", "Cuenta2 + Cuenta4", "Malcorra");
        	this.procesador2.cargarIndPredefinidos("Indicador2", "Cuenta1 * Cuenta2", "Sancor");
        	this.procesador3.cargarIndPredefinidos("Indicador3", "Cuenta1 - Cuenta3", "Correa");
        }
        
        
        
        @Test
        public void calcular(){
             assertEquals(this.procesador1.getIndicadores().get(0).operar("Cuenta2 + Cuenta4"),1700);
        }
        
        
        
}
