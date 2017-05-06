package ar.edu.utn.dds.PROCESARARCHIVO;


import java.util.ArrayList;

public class Indicador implements IOperacion{
	private String nombre;
	private String operacion;
	private String empresa;
	private LeerArchivo lector;
	private ArrayList<Cuenta> cuentasEmpresa;
	private ProcesarIndicadores p;
	
	
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
	//busco la empresa sobre la que quiero calcular el indicador para obtener las cuentas de esa empresa sobre las que puede aplicar indicador
	public void buscarEmpresa(String nombreEmpresa){
		try{
		for(int e=0;e<lector.getEmpresas().size();e++){
			if(lector.getEmpresas().get(e).getNombre().equals(nombreEmpresa)){
				this.setCuentasEmpresa(lector.getEmpresas().get(e).getCuentas());
			}
		
		}
		}catch(Exception e){
		  
		};
		return;
	}
	private void descomponerString(String operaciones){
		char[] c=operaciones.toCharArray();
		StringBuilder s= new StringBuilder();
		
		for(int i=0;i<c.length;i++){
			
			if (c[i] =='+' ||c[i] == '-' ||c[i] =='(' ||c[i] == ')' ||c[i] =='*' ||c[i] == '/'  ){
				
				if(s.length()==0){
					//lista.add(s.toString());
					Indicador ind=this.buscarIndicador(s.toString());
					if(ind!=null){
						String x=ind.getOperacion();
						getOperaciones().add(x);//agrego una operacion
						this.descomponerString(x);
						x=null;
					}
					else{
						Cuenta cue=buscarCuenta(s.toString());
						getOperaciones().add(String.valueOf(cue.getValor()));
					}
					
					
					
					s= new StringBuilder();
				}
				getOperaciones().add(""+c[i]);
			
			}
				
			else {
				s.append(c[i]);
			    						
			}
			
		}
		
		
	}
	
	private String armarExpresion() {
		String s=null;
		for(int i=0;i<this.getOperaciones().size();i++){
			s.concat(this.getOperaciones().get(i));
		}
		return s;
	}
        
	private Cuenta buscarCuenta(String s){
		
		for(int i=0; i< this.getCuentasEmpresa().size();i++){
			if(this.getCuentasEmpresa().get(i).getNombre().equals(s)){
				return this.getCuentasEmpresa().get(i);
			}
		}
		return null;
		
	}
		
	private int calcularIndicador(){
		
		this.descomponerString(this.getOperacion());
		return parser.parse(this.armarExpresion());
		
		
	}

	
	private Indicador buscarIndicador(String s) {
		// TODO Auto-generated method stub
		
		for(int i=0; i< p.getIndicadores().size();i++){
			if(p.getIndicadores().get(i).getNombre().equals(s)){
				return p.getIndicadores().get(i);
			}
		}
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
	public ArrayList<Cuenta> getCuentasEmpresa() {
		return cuentasEmpresa;
	}
	public void setCuentasEmpresa(ArrayList<Cuenta> cuentasEmpresa) {
		this.cuentasEmpresa = cuentasEmpresa;
	}
}
