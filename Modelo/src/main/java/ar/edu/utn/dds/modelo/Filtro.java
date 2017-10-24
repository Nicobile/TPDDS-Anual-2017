package ar.edu.utn.dds.modelo;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

@Entity  
@DiscriminatorValue("condicionfiltro") 

public class Filtro extends Condicion {

    public Filtro() {
    	
    }
	
	public Filtro(ValorCalculable ladoIzq, Periodo periodos, int anios) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
		super.setAnios(anios);
	}

	public Filtro(ValorCalculable ladoIzq, int anios) {
		super.setLadoIzq(ladoIzq);
		super.setAnios(anios);
	}

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {
		setFiltro(true);

		return super.aplicar();
	}
}
