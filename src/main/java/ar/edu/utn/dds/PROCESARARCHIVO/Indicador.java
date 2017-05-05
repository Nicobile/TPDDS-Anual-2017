package ar.edu.utn.dds.PROCESARARCHIVO;

public class Indicador implements IOperacion{
	private String nombre;
	private String operacion;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public int operar(String operacion){
		
	}
	@Override
	public int calcular() {
		return operar(this.getOperacion());
	}
}
