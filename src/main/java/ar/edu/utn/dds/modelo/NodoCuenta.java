package ar.edu.utn.dds.modelo;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;

import java.util.List;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class NodoCuenta implements Operando {
	private String nomCuenta;
	
	@Override
	public double calcular(Empresa e, Periodo periodo) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		List<Cuenta> cuentas = e.buscarUnaCuentaPorPeriodo(nomCuenta, periodo);
		
		
		
		double valor = 0;
		for (int i = 0; i < cuentas.size(); i++) {
			valor += cuentas.get(i).getValor();
		}

		return valor;

	}

	public NodoCuenta(String nomCuenta) {

		this.nomCuenta = nomCuenta;

	}

}
