package ar.edu.utn.dds.modelo;

import java.time.LocalDate;

public class Periodo {
private LocalDate fechaInicio;
private LocalDate fechaFin;
public LocalDate getFechaInicio() {
	return fechaInicio;
}
public void setFechaInicio(LocalDate fechaInicio) {
	this.fechaInicio = fechaInicio;
}
public LocalDate getFechaFin() {
	return fechaFin;
}
public void setFechaFin(LocalDate fechaFin) {
	this.fechaFin = fechaFin;
}
public Periodo(LocalDate fechaInicio, LocalDate fechaFin) {
	super();
	this.fechaInicio = fechaInicio;
	this.fechaFin = fechaFin;
}



}
