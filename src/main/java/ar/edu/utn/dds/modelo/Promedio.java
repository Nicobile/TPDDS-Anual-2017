package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

public class Promedio extends LadoIzq {

	public Promedio(Indicador indicador) {
		super(indicador);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<PuntajeEmpresa> calcularValor(ArrayList<Empresa> empresas,int periodos){
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(empresas, periodos);
		/*
		 * deberia filtrar la lista de cuentas de le empresa durante los ultimos periodos
		 * aplico el indicador
		 * a cada uno de los elementos de la lista firltrada
		 * y ordeno por */
		
		
		
	}

}
