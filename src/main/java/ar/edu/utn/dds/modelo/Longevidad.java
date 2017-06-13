package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Longevidad extends LadoIzq {

	public Longevidad(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
	
		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
	
	for (int i=0;i<getTraductor().getEmpresas().size();i++){
		
	int x=getTraductor().getEmpresas().get(i).obtenerLaFechaDeLaCuentaMasAntigua();

	for(int j=0;j<listaEmpresas.size();j++){
		if (listaEmpresas.get(j).getNombreEmpresa().equals(getTraductor().getEmpresas().get(i).getNombre())){
			listaEmpresas.get(j).setResultadoDeAplicarCondicion(2017-x);
		}
	}
	
	}
	
	return listaEmpresas;
	}
}
