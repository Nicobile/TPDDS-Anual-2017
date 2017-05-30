package ar.edu.utn.dds.modelo;

public class Division implements Operador {
	public double operar(double operando1, double operando2) {
		if(operando2 == 0){
			throw new java.lang.ArithmeticException("/ by zero");
		}
		return operando1 / operando2;
	}
}
