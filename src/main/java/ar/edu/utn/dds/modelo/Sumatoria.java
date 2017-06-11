package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Sumatoria extends LadoIzq{

	public Sumatoria(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
		/*
		 * deberia filtrar la lista de cuentas de le empresa durante los ultimos periodos
		 * aplico el indicador
		 * a cada uno de los elementos de la lista firltrada
		 * y ordeno por */
		return super.sumatoriaIndicadores(listaEmpresas, periodos);
		
		
	}
}
