package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

@Entity  
@DiscriminatorValue("creciente") 

public class Creciente extends ValorCalculable {

	public Creciente(Indicador indicador, Traductor t) {
		super(indicador, t);
	}
	
	public Creciente() {
		
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		ArrayList<PuntajeEmpresa> listaEmpresas = super.calcularValor(periodos, anios);
		List<Empresa> empresas = getTraductor().empresasConIndicadorCreciente(getEmpresas(), anios, getIndicador());

		return eliminarEmpresasQueNoCumplenCondicion(listaEmpresas, empresas);

	}
}
