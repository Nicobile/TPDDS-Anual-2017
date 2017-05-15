package ar.edu.utn.dds.modelo;

public class NodoNumero implements Operando {
	private int numero;

	@Override
	public int calcular() {
		// TODO Auto-generated method stub
		return numero;
	}
/*
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}*/

	public NodoNumero(int numero) {
		super();
		this.numero = numero;
	}

}
