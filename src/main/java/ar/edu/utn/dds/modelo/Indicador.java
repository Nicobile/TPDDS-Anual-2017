package ar.edu.utn.dds.modelo;


import ar.edu.utn.dds.parser.ExpressionParser;

public class Indicador {
	private String nombre;
	private String operacion;
	private String nombreEmpresa;
	private ExpressionParser parser = new ExpressionParser();
	

	public int operar(String operacion){
		
		return parser.parse(operacion);
		
	}
	


	
	public Indicador(String nombre,String operacion, String nombreEmpresa) {
		super();
		this.nombre = nombre;
		this.operacion = operacion;
		this.nombreEmpresa = nombreEmpresa;
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
	

	public String getOperacion() {
		return operacion;
	}




	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}




	public ExpressionParser getParser() {
		return parser;
	}
	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}
	

	
	
	
}
