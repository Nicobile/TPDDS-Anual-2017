package ar.edu.utn.dds.modelo;



public class Indicador extends NodoIndicador{
	private String nombre;
	private Operacion operacion;
	private String nombreEmpresa;
	private String fecha;
	//ver de sacar la fecha

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

	

	public Indicador( String nombre,String nombreEmpresa, String fecha) {
		super();
		this.nombre = nombre;
		this.nombreEmpresa = nombreEmpresa;
		this.fecha = fecha;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}


}
