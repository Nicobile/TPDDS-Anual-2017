package ar.edu.utn.dds.modelo;

public class NodoNumero implements Operando {
	private int numero;

	@Override
	public double calcular(Empresa e, Periodo periodo) {

		return numero;
	}

	public NodoNumero(int numero) {
		super();
		this.numero = numero;
	}

}
