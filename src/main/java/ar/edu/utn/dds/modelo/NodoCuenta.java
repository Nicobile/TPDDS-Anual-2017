package ar.edu.utn.dds.modelo;

import excepciones.NoSeEncuentraEnLaLista;

public class NodoCuenta implements Operando {
	private String nomCuenta;

	@Override
	public double calcular(Empresa e, String fecha) throws NoSeEncuentraEnLaLista {
		// TODO Auto-generated method stub

		Cuenta c = e.buscarUnaCuentaPorFecha(nomCuenta, fecha);

		return c.getValor();

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;
	}

}
