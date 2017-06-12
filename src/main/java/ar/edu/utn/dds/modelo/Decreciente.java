package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Decreciente extends LadoIzq{
	
	
	
	
	public Decreciente(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{

		ArrayList<PuntajeEmpresa> listaEmpresas= super.calcularValor(periodos);
		ArrayList<Empresa> empresas = getTraductor().compararAListaDeEmpresas(getEmpresas(), periodos, getIndicador(),'<');
		
		return eliminarEmpresasQueNoCumplenCondicion( listaEmpresas,  empresas);
		
	}
}
