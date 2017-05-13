package ar.edu.utn.dds.modelo;

public class NodoIndicador implements Operando{
	private Operador operador;
	private Operando operando1;
	private Operando operando2;


		
	/*	
		public NodoIndicador(Operador operador, Operando operando1, Operando operando2) {
		super();
		this.operador = operador;
		this.operando1 = operando1;
		this.operando2 = operando2;
	}*/

	

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
