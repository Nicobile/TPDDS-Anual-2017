package ar.edu.utn.dds.modelo;

import excepciones.NoSeEncuentraEnLaLista;

public interface Operando {
	public double calcular(Empresa e, String fecha) throws NoSeEncuentraEnLaLista;

}
