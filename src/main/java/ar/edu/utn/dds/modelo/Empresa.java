package ar.edu.utn.dds.modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

public class Empresa {

	private String nombre;

	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

	public Empresa(String nombre, ArrayList<Cuenta> cuentas) {
		this.nombre = nombre;

		this.cuentas = cuentas;
	}

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) throws NoSeEncuentraLaCuentaException {
try{
	return this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
			.findFirst().get();
}
	catch (NoSuchElementException e){

		throw new NoSeEncuentraLaCuentaException("No se encuentra la cuenta");}

	}
	public void agregarCuenta(Cuenta cuenta){
		
		getCuentas().add(cuenta);
		//ordenado por fecha de inicio
		Collections.sort(getCuentas(),
				(p1, p2) -> p1.getPeriodo().getFechaInicio().compareTo(p2.getPeriodo().getFechaInicio()));
	}

	public Empresa(String nombre) {
		super();
		this.nombre = nombre;
		this.cuentas= new ArrayList<Cuenta>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuentas == null) ? 0 : cuentas.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Empresa other = (Empresa) obj;
		if (cuentas == null) {
			if (other.cuentas != null)
				return false;
		} else if (!cuentas.equals(other.cuentas))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public Cuenta buscarUnaCuentaPorPeriodo(String nombreDeCuenta,Periodo periodo)
			throws NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
		
		try{return filtraCuentasEnPeriodo(periodo).stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getPeriodo().equals(periodo) ).findFirst().get();}
		catch (NoSuchElementException e){
		
			try{
				getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
				.findFirst().get();
			}
			// no encontre la cuenta
			catch (NoSuchElementException x){
				throw new NoSeEncuentraLaCuentaException("No se encuentra la cuenta");
			}
			// no encontre una cuenta en esa fecha
		
				throw new NoSeEncuentraLaCuentaEnElPeriodoException(
						"No se encontro para la empresa una cuenta en el periodo ");
			}
		}

	

	public List<Cuenta> filtraCuentasEnPeriodo(Periodo periodo) {
		return  getCuentas().stream().filter(unaC -> (unaC.getPeriodo().getFechaInicio().isAfter(periodo.getFechaInicio()))&&(unaC.getPeriodo().getFechaFin().isBefore(periodo.getFechaFin()))).collect(Collectors.toList());
	}

	public double obtenerValorDeCuenta(String nombreDeCuenta, Periodo periodo)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {
	
		return buscarUnaCuentaPorPeriodo(nombreDeCuenta, periodo).getValor();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}


}
