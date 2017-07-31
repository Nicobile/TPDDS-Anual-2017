package ar.edu.utn.dds.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ar.edu.utn.dds.excepciones.FechaInicioPosteriorAFinException;

public class Periodo {
@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periodo other = (Periodo) obj;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		return true;
	}
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
public Periodo(String fechaInicio, String fechaFin) {
	super();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate fechaI = LocalDate.parse(fechaInicio, formatter);
	LocalDate fechaF = LocalDate.parse(fechaFin, formatter);
	if(fechaI.isAfter(fechaF))throw new FechaInicioPosteriorAFinException("La fecha inicio es posterior a la fecha fin");
	this.fechaInicio = fechaI;
	this.fechaFin = fechaF;
}



}
