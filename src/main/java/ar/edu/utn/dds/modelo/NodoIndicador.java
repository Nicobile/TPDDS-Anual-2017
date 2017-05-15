package ar.edu.utn.dds.modelo;

import java.util.ArrayList;


public class NodoIndicador implements Operando{
	private Operador operador;
	private Operando operando1;
	private Operando operando2;
	
	private String nombre;
	private ArrayList<Indicador> indicadores;


		
		
		public NodoIndicador( Operando operando1,Operador operador, Operando operando2) {
		super();
		this.operador = operador;
		this.operando1 = operando1;
		this.operando2 = operando2;
	}

			



		public NodoIndicador(String nombre, ArrayList<Indicador> indicadores) {
			super();
			this.nombre = nombre;
			this.indicadores = indicadores;
		}







		@Override
		public int calcular() {
			// TODO Auto-generated method stub
			return this.getOperador().operar(operando1.calcular(),operando2.calcular());
		} //extiende a indicador

		


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
