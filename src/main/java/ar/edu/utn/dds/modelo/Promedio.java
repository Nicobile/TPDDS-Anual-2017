package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Promedio extends ValorCalculable {

	public Promedio(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
	
		
		  ArrayList<PuntajeEmpresa> sumatoria =  super.sumatoriaIndicadores(listaEmpresas, periodos);
	      
	      for (int i=0;(i<sumatoria.size());i++){
	    	  sumatoria.get(i).setResultadoDeAplicarCondicion(sumatoria.get(i).getResultadoDeAplicarCondicion()/periodos);
	      }
	      return sumatoria;		
			
		
	}

}
