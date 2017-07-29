package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Arrays;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Mediana extends ValorCalculable {

	public Mediana(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos);

	//	Double[] array = new Double[periodos];
/*
		for (int j = 0; j < getEmpresas().size(); j++) {

			for (int i = periodos; i > 0; i--) {
				array[i] = getTraductor().calcular(getEmpresas().get(j).getNombre(), String.valueOf(2017 - i),
						getIndicador().getNombre());// calculo el indicador para
													// una empresa y guardo el
													// resultado en un array
				// para desps obtener la mediana
			}

			int empresa = j;

			Arrays.sort(array);
			double mediana;
			if (array.length % 2 == 0) {
				mediana = (array[array.length / 2] + array[array.length / 2 - 1]) / 2;
			} else {
				mediana = array[array.length / 2];
			}
			listaEmpresas.stream()
					.filter(unaE -> unaE.getNombreEmpresa().equals(getEmpresas().get(empresa).getNombre())).findFirst()
					.get().setResultadoDeAplicarCondicion(mediana);
		}*/
		return listaEmpresas;

	}
}
