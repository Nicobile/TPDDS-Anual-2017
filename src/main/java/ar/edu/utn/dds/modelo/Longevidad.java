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
	
	for (int i=0;i<getEmpresas().size();i++){
		
		for(int x=periodos;x>0;x--)
		{int fecha=2016-x;
			
		if ((getEmpresas().get(i).getCuentas().stream().filter(unaC -> unaC.getFecha().equals(fecha)).findFirst().isPresent())){		
		
		System.out.println("asdasd");
		
		}	
		
		else{
			
		
		
			}
	}}
	return listaEmpresas;
	}
}
