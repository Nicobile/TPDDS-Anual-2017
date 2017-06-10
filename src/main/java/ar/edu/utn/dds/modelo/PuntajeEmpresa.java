package ar.edu.utn.dds.modelo;

public class PuntajeEmpresa {
private String nombreEmpresa;
private int valor=0;
public void suma(int v){
	valor=valor+v;	
}
public String getNombreEmpresa() {
	return nombreEmpresa;
}
public void setNombreEmpresa(String nombreEmpresa) {
	this.nombreEmpresa = nombreEmpresa;
}
public int getValor() {
	return valor;
}
public void setValor(int valor) {
	this.valor = valor;
}
}
