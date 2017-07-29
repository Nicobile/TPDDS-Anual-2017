package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class NodoIndicador implements Operando {
	private Operador operador;
	private Operando operando1;
	private Operando operando2;
	ExpressionParser parser;

	public NodoIndicador(Operando operando1, Operador operador, Operando operando2) {
		super();
		this.operador = operador;
		this.operando1 = operando1;
		this.operando2 = operando2;
	}

	public NodoIndicador(String nombre, ArrayList<Indicador> indicadores) {
		Indicador i = indicadores.stream().filter(unIndicador -> unIndicador.getNombre().equals(nombre)).findFirst()
				.get();
		NodoIndicador o = (NodoIndicador) new ExpressionParser().parse(i.getOperacion(), indicadores);
		operador = o.getOperador();
		operando1 = o.getOperando1();
		operando2 = o.getOperando2();

	}

	@Override
	public double calcular(Empresa e, Periodo periodo)
			throws NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraLaEmpresaException {
		// TODO Auto-generated method stub
		return this.getOperador().operar(operando1.calcular(e, periodo), operando2.calcular(e, periodo));
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Operando getOperando1() {
		return operando1;
	}

	public void setOperando1(Operando operando1) {
		this.operando1 = operando1;
	}

	public Operando getOperando2() {
		return operando2;
	}

	public void setOperando2(Operando operando2) {
		this.operando2 = operando2;
	}

}
