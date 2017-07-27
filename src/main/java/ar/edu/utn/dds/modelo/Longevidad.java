package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Longevidad extends ValorCalculable {

	public Longevidad(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(int periodos) throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnEsaFechaException, NoSeEncuentraElIndicadorException {

		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos);
//CREO QUE LA LINGEVIDAD ES TRIBUTO DE LA EMPRESAENTONCES OBTENTER... NO HARIA FALTA
		for (int i = 0; i < getTraductor().getEmpresas().size(); i++) {

			int x = getTraductor().getEmpresas().get(i).obtenerLaFechaDeLaCuentaMasAntigua();

			for (int j = 0; j < listaEmpresas.size(); j++) {
				if (listaEmpresas.get(j).getNombreEmpresa().equals(getTraductor().getEmpresas().get(i).getNombre())) {
					listaEmpresas.get(j).setResultadoDeAplicarCondicion(2017 - x);
				}
			}

		}

		return listaEmpresas;
	}
}
