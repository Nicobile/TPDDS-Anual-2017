package ar.edu.utn.dds.modelo;

import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class CondicionFiltro extends Condicion {

	public CondicionFiltro(ValorCalculable ladoIzq, int periodos) {
		super.setLadoIzq(ladoIzq);
		super.setPeriodos(periodos);
	}

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
			NoSeEncuentraElIndicadorException {
		setFiltro(false);

		return super.aplicar();
	}

}
