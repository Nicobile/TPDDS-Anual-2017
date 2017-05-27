package ar.edu.utn.dds.procesarArchivos;

public class LineaArchivo {

	String nombreEmpresa;
	String nombreCuenta;
	double valorCuenta;
	String fecha;

	public LineaArchivo(String nombreEmpresa, String nombreCuenta, double valorCuenta, String fecha) {
		this.nombreCuenta = nombreCuenta;
		this.nombreEmpresa = nombreEmpresa;
		this.valorCuenta = valorCuenta;
		this.fecha = fecha;

	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public double getValorCuenta() {
		return valorCuenta;
	}

	public void setValorCuenta(int valorCuenta) {
		this.valorCuenta = valorCuenta;
	}

}
