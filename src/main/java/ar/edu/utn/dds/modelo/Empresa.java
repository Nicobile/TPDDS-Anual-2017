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

	private List<Cuenta> cuentas;

	public Empresa(String nombre, ArrayList<Cuenta> cuentas) {
		this.nombre = nombre;

		this.cuentas = cuentas;
	}

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) throws NoSeEncuentraLaCuentaException {
		try {
			return this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
					.findFirst().get();
		} catch (NoSuchElementException e) {

			throw new NoSeEncuentraLaCuentaException("No se encuentra la cuenta");
		}

	}

	public void agregarCuenta(Cuenta cuenta) {

		getCuentas().add(cuenta);
		// ordenado por fecha de inicio
		Collections.sort(getCuentas(),
				(p1, p2) -> p1.getPeriodo().getFechaInicio().compareTo(p2.getPeriodo().getFechaInicio()));
	}

	public Empresa(String nombre) {
		super();
		this.nombre = nombre;
		this.cuentas = new ArrayList<Cuenta>();
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

	public List<Cuenta> buscarUnaCuentaPorPeriodo(String nombreDeCuenta, Periodo periodo)
			throws NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		List<Cuenta> cuentas = filtraCuentasEnPeriodo(periodo).stream()
				.filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta)).collect(Collectors.toList());

		if (cuentas.isEmpty()) {
			try {
				buscarUnaCuenta(nombreDeCuenta);
			} catch (NoSeEncuentraLaCuentaException e) {
				throw new NoSeEncuentraLaCuentaException("No existe la cuenta para esa empresa");
			}
			throw new NoSeEncuentraLaCuentaEnElPeriodoException(
					"No se encontro para la empresa la cuenta en el periodo ");
		}

		return cuentas;
	}

	public List<Cuenta> filtraCuentasEnPeriodo(Periodo periodo) {

		return getCuentas().stream()
				.filter(unaC -> ((unaC.getPeriodo().getFechaInicio().isAfter(periodo.getFechaInicio()))
						&& (unaC.getPeriodo().getFechaFin().isBefore(periodo.getFechaFin())))
						|| (unaC.getPeriodo().equals(periodo)))
				.collect(Collectors.toList());

	}

	public double obtenerValorDeCuenta(String nombreDeCuenta, Periodo periodo) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		List<Cuenta> cuentas = buscarUnaCuentaPorPeriodo(nombreDeCuenta, periodo);
		double valor = 0;
		for (int i = 0; i < cuentas.size(); i++) {
			valor += cuentas.get(i).getValor();
		}

		return valor;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

}
