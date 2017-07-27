package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Promedio extends ValorCalculable {

	public Promedio(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException, NoSeEncuentraElIndicadorException{
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
	
		
		  ArrayList<PuntajeEmpresa> sumatoria =  super.sumatoriaIndicadores(listaEmpresas, periodos);
	      
	      for (int i=0;(i<sumatoria.size());i++){
	    	  sumatoria.get(i).setResultadoDeAplicarCondicion(sumatoria.get(i).getResultadoDeAplicarCondicion()/periodos);
	      }
	      return sumatoria;		
			
		
	}

}
