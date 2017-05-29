package ar.edu.utn.dds.modelo;

import excepciones.NoSeEncuentraCuenta;
import excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import excepciones.NoSeEncuentraLaEmpresa;

public interface Operando {
	public double calcular(Empresa e, String fecha) throws NoSeEncuentraLaEmpresa, NoSeEncuentraCuenta, NoSeEncuentraLaCuentaEnEsaFecha;

}
