package ar.edu.utn.dds.modelo;

public class NodoCuenta implements Operando {
	private String nomCuenta;
	private Empresa e;
	private String fecha;
	
	
	@Override
	public int calcular() {
		// TODO Auto-generated method stub
		Cuenta c=e.buscarUnaCuentaPorFecha(nomCuenta, fecha);
		return c.getValor();
	}


	public NodoCuenta(String nomCuenta) {
		super();
		this.nomCuenta = nomCuenta;
	}
	
}
