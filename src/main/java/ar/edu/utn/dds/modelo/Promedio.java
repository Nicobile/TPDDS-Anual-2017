package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Promedio extends LadoIzq {

	public Promedio(Indicador indicador) {
		super(indicador);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
		/*
		 * deberia filtrar la lista de cuentas de le empresa durante los ultimos periodos
		 * aplico el indicador
		 * a cada uno de los elementos de la lista firltrada
		 * y ordeno por */
		
	  ArrayList<PuntajeEmpresa> sumatoria =  super.sumatoriaIndicadores(listaEmpresas, periodos);
      
      for (int i=0;(i<sumatoria.size());i++){
    	  sumatoria.get(i).setResultadoDeAplicarCondicion(sumatoria.get(i).getResultadoDeAplicarCondicion()/periodos);
      }
      return sumatoria;
		
		
		
	}

}
