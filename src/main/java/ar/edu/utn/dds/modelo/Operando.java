package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public interface Operando {
	public double calcular(Empresa e, String fecha)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException;

}
