package ar.edu.utn.dds.modelo;

import excepciones.NoSeEncuentraCuenta;
import excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import excepciones.NoSeEncuentraLaEmpresa;

public class NodoCuenta implements Operando {
	private String nomCuenta;

	@Override
	public double calcular(Empresa e, String fecha) throws NoSeEncuentraLaEmpresa, NoSeEncuentraCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		// TODO Auto-generated method stub

		Cuenta c = e.buscarUnaCuentaPorFecha(nomCuenta, fecha);

		return c.getValor();

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;
	}

}
