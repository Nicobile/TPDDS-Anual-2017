package ar.edu.utn.dds.PROCESARARCHIVO;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class Indicador implements IOperacion{
	private String nombre;
	private String operacion;
	private String empresa;
	private LeerArchivo lector;
	
	 public LeerArchivo getLector() {
		return lector;
	}
	public void setLector(LeerArchivo lector) {
		this.lector = lector;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	ArrayList<String> operaciones =new ArrayList<String>();
	ExpressionParser parser = new ExpressionParser();
	
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
	public int operar(String operacion){
		
		return parser.parse(operacion);
		
	}
	
	public ArrayList<Cuenta> buscarEmpresa(String nombreEmpresa){
		try{
		for(int e=0;e<lector.getEmpresas().size();e++){
			if(lector.getEmpresas().get(e).getNombre().equals(nombreEmpresa)){
				return lector.getEmpresas().get(e).getCuentas();
			}
		
		}
		}catch(Exception e){
		  
		};
		return null;
	}
	

	
	public ArrayList<String> getOperaciones() {
		return operaciones;
	}
	public void setOperaciones(ArrayList<String> operaciones) {
		this.operaciones = operaciones;
	}
	public ExpressionParser getParser() {
		return parser;
	}
	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}
	@Override
	public int calcular() {
		return operar(this.getOperacion());
	}
}
