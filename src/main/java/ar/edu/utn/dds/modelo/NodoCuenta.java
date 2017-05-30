package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class NodoCuenta implements Operando {
	private String nomCuenta;

	@Override
	public double calcular(Empresa e, String fecha)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		// TODO Auto-generated method stub

		Cuenta c = e.buscarUnaCuentaPorFecha(nomCuenta, fecha);

		return c.getValor();

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;
	}

}
