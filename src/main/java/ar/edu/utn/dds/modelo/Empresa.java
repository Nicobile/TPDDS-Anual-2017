package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
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

	public Cuenta buscarUnaCuentaPorFecha(String nombreDeCuenta, String fecha)
			throws NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException {
		try{return this.getCuentas().stream().filter(
				unaCuenta -> unaCuenta.getNombre().equals(nombreDeCuenta) && unaCuenta.getFecha().equals(fecha))
				.findFirst().get();}
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
		
				throw new NoSeEncuentraLaCuentaEnEsaFechaException(
						"No se encontro para la empresa una cuenta en la fecha especificada ");
			}
		}

	

	public void filtraCuentasEnPeriodo(String p) {
		getCuentas().stream().filter(unaC -> unaC.getFecha().equals(p));
	}

	public double obtenerValorDeCuenta(String nombreDeCuenta, String fecha)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException {
		return buscarUnaCuentaPorFecha(nombreDeCuenta, fecha).getValor();

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
// Este metodo habria que sacarlo porque creo que la LONGEVIDAD ES TRIBUTO DE LA EMPRESA
	public int obtenerLaFechaDeLaCuentaMasAntigua() {
		int fechaMasAntigua = 0;
		int xx = Integer.valueOf(this.getCuentas().get(0).getFecha());
		for (int i = 0; i < this.getCuentas().size(); i++) {

			if (Integer.valueOf(this.getCuentas().get(i).getFecha()) <= xx) {
				fechaMasAntigua = Integer.valueOf(getCuentas().get(i).getFecha());
				xx = Integer.valueOf(getCuentas().get(i).getFecha());
			}
		}
		return fechaMasAntigua;
	}

}
