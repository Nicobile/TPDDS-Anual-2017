package ar.edu.utn.dds.modelo;

public class PuntajeEmpresa {
private String nombreEmpresa;
private int puntaje=0;
private double resultadoDeAplicarCondicion;



public PuntajeEmpresa(String nombreEmpresa) {
	super();
	this.nombreEmpresa = nombreEmpresa;
}

	public void suma(int v){
	puntaje=puntaje+v;	
}
public String getNombreEmpresa() {
	return nombreEmpresa;
}
public void setNombreEmpresa(String nombreEmpresa) {
	this.nombreEmpresa = nombreEmpresa;
}
public int getPuntaje() {
	return puntaje;
}
public void setPuntaje(int puntaje) {
	this.puntaje = puntaje;
}
public double getResultadoDeAplicarCondicion() {
	return resultadoDeAplicarCondicion;
}
public void setResultadoDeAplicarCondicion(double resultadoDeAplicarCondicion) {
	this.resultadoDeAplicarCondicion = resultadoDeAplicarCondicion;
}

}
