package ar.edu.utn.dds.procesarArchivos;



public class LineaArchivo {

	String nombreEmpresa;
	String nombreCuenta;
	double valorCuenta;
	String fechaInicio;
	String fechaFin;
	

	public LineaArchivo(String nombreEmpresa, String nombreCuenta, double valorCuenta,String fechaInicio,String fechaFin)  {
		this.nombreCuenta = nombreCuenta;
		this.nombreEmpresa = nombreEmpresa;
		this.valorCuenta = valorCuenta;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;

	}

	





	public String getFechaInicio() {
		return fechaInicio;
	}







	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}







	public String getFechaFin() {
		return fechaFin;
	}







	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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
