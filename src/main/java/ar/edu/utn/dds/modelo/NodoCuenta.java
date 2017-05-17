package ar.edu.utn.dds.modelo;

public class NodoCuenta implements Operando {
	private String nomCuenta;
	
	
	
	@Override
	public int calcular(Empresa e,String fecha) {
		// TODO Auto-generated method stub
		try{
		Cuenta c=e.buscarUnaCuentaPorFecha(nomCuenta, fecha);
		
		return c.getValor();}catch(Exception err){System.out.println("NO SE ENCONTRO LA CUENTA");};
		return -1;
		
	}


	public NodoCuenta(String nomCuenta) {
		
		this.nomCuenta = nomCuenta;
	}
	
}
