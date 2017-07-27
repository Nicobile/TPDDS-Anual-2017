package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Decreciente extends ValorCalculable {

	public Decreciente(Indicador indicador, Traductor traductor) {
		super(indicador, traductor);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PuntajeEmpresa> calcularValor(int periodos)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnEsaFechaException, NoSeEncuentraElIndicadorException {

		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos);
		List<Empresa> empresas = getTraductor().empresasConIndicadorDecreciente(getEmpresas(), periodos,
				getIndicador());

		return eliminarEmpresasQueNoCumplenCondicion(listaEmpresas, empresas);

	}
}
