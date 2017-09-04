package ar.edu.utn.dds.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

@Entity
@Table(name = "empresas")
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "nombre")
	private String nombre;
	private LocalDate fechaInscripcion;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Cuenta> cuentas;

	/* EMPRESA */

	public Empresa(String nombre, List<Cuenta> cuentas) {
		this.nombre = nombre;
		this.cuentas = cuentas;
	}

	public Empresa() {

	}

	public Empresa(String nombre, String fechaInscripcion) {
		super();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaI = LocalDate.parse(fechaInscripcion, formatter);
		this.nombre = nombre;
		this.fechaInscripcion = fechaI;
		this.cuentas = new ArrayList<Cuenta>();
	}

	/* CUENTA */

	// AGREGO

	public void agregarCuenta(Cuenta cuenta) {

		getCuentas().add(cuenta);

		Collections.sort(getCuentas(),
				(p1, p2) -> p1.getPeriodo().getFechaInicio().compareTo(p2.getPeriodo().getFechaInicio()));
	}

	// BUSCO

	public Cuenta buscarUnaCuenta(String nombreDeCuenta) throws NoSeEncuentraLaCuentaException {
		try {
			return this.getCuentas().stream().filter(unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta))
					.findFirst().get();
		} catch (NoSuchElementException e) {
			throw new NoSeEncuentraLaCuentaException("No se encuentra la cuenta");
		}
	}

	// (NOMBRE - PERIODO) -> VALOR

	public double consultarValorCuenta(String nombreCuenta, Periodo periodo) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException {

		return obtenerValorDeCuenta(nombreCuenta, periodo);
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

	// (NOMBRE - PERIODO) -> CUENTAS

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

	/* FILTRO */

	public List<Cuenta> filtraCuentasEnPeriodo(Periodo periodo) {

		return getCuentas().stream()
				.filter(unaC -> ((unaC.getPeriodo().getFechaInicio().isAfter(periodo.getFechaInicio()))
						&& (unaC.getPeriodo().getFechaFin().isBefore(periodo.getFechaFin())))
						|| (unaC.getPeriodo().equals(periodo)))
				.collect(Collectors.toList());

	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
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
		if (fechaInscripcion == null) {
			if (other.fechaInscripcion != null)
				return false;
		} else if (!fechaInscripcion.equals(other.fechaInscripcion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
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

	public LocalDate getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

}
