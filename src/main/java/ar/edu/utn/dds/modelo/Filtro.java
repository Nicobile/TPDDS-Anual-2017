package ar.edu.utn.dds.modelo;

import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class Filtro extends Condicion {

	public Filtro(ValorCalculable ladoIzq, Periodo periodos, int anios) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
		super.setAnios(anios);
	}

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {
		setFiltro(false);

		return super.aplicar();
	}

}
