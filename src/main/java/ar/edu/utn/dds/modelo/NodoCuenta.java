package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class NodoCuenta implements Operando {
	private String nomCuenta;

	@Override
	public double calcular(Empresa e, String fecha)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException {
		// TODO Auto-generated method stub

		Cuenta c = e.buscarUnaCuentaPorFecha(nomCuenta, fecha);

		return c.getValor();

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;
	}

}
