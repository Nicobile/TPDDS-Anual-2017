package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;


import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class NodoCuenta implements Operando {
	private String nomCuenta;


	@Override
	public double calcular(Empresa e, Periodo periodo)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		// TODO Auto-generated method stub
		
		Cuenta c = e.buscarUnaCuentaPorPeriodo(nomCuenta, periodo);

		return c.getValor();

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;
		
	}

}
