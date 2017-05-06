package ar.edu.utn.dds.PROCESARARCHIVO;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {
	public LeerArchivo lector;//me permite conocer a la lista de empresas para buscar sobre la cual estoy calculando
	public ArrayList<Cuenta> cuentasEmpresa;//de la empresa sobre la que el indicador esta trabajando, me guardo todas sus cuentas para utilizarla para calcular el indicador
	 public ArrayList<String> operaciones =new ArrayList<String>();//operaciones es una lista que va descomponiendo la operacion de los indicadores hasta llegar a un ponto en donde son todos cuentas y operadores
	public ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	   SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");//declaro el formato de la fecha
    
	   
public ProcesarIndicadores(LeerArchivo lector, ArrayList<Cuenta> cuentasEmpresa,
			ArrayList<Indicador> indicadores) {
		super();
		this.lector = lector;
		this.cuentasEmpresa = cuentasEmpresa;
		this.indicadores = indicadores;
	}
//leo el excel y lo cargo en la lista de indicadores con el nombre del indicador y la operacion ver si al excel agregar la empresa
	//formato de excel columna 1 nombre indicador columna 2 operacion columna 3 nombre de la empresa
	void leerExcel() throws IOException ,FileNotFoundException  {
		
       File inputWorkbook = new File(this.getClass().getResource("/Indicadores.xls").getFile());			
        Workbook w=null;
        try {
         w = Workbook.getWorkbook(inputWorkbook);                      
         Sheet sheet = w.getSheet(0);                       
         //for (int j = 0; j < sheet.getColumns(); j++) {
            
        	 for (int i = 0; i < sheet.getRows(); i++) {
                  Cell cell = sheet.getCell(0, i);
                  Cell cell2 = sheet.getCell(1, i);
                  Cell cell3 = sheet.getCell(2, i);
                   CellType type = cell.getType();
                   Indicador indicador= new Indicador();
                  
                   if(type== CellType.LABEL){
                	   
                	   //seteo el nombre del indicador
                	   indicador.setNombre(cell.getContents());                                      
                	   indicador.setOperacion(cell2.getContents());
                	   indicador.setNombreEmpresa(cell3.getContents());
                	   indicadores.add(indicador);
                        
                        }}
                        
                } catch (BiffException e){e.printStackTrace();}
      
        }
	
	public void cargarIndPredefinidos(String nombre, String operacion,String nombreDeEmpresa){
		Indicador indicador=new Indicador();
		indicador.setNombre(nombre);
		indicador.setOperacion(operacion);
		indicador.setNombreEmpresa(nombreDeEmpresa);
		indicadores.add(indicador);
	 }
       	

	private void descomponerString(String operaciones){
			char[] c=operaciones.toCharArray();
			StringBuilder s= new StringBuilder();
			
			for(int i=0;i<c.length;i++){
				
				if (c[i] =='+' ||c[i] == '-' ||c[i] =='(' ||c[i] == ')' ||c[i] =='*' ||c[i] == '/'  ){
					
					
					if(s.length()>0){
						//lista.add(s.toString());
						Indicador ind=this.buscarIndicador(s.toString());
						if(ind!=null){
							String x=new String();
									x=ind.getOperacion();
							//getOperaciones().add(x);//agrego una operacion
							this.descomponerString(x);
							
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
			String s="";
			for(int i=0;i<this.getOperaciones().size();i++){
				s.concat(this.getOperaciones().get(i));
			}
			return s;
		}
	        
	
			
		public int calcularIndicador(Indicador i){
			
			this.descomponerString(i.getOperacion());
			return i.operar(this.armarExpresion());//le paso al indicador la expresion ya armada para que la calcule
			
			
		}
		//busco la empresa sobre la que quiero calcular el indicador para obtener las cuentas de esa empresa sobre las que puede aplicar indicador
				public void buscarEmpresa(Indicador i) {
					try{
					for(int e=0;e<lector.getEmpresas().size();e++){
						if(lector.getEmpresas().get(e).getNombre().equals(i.getNombreEmpresa())){
							this.setCuentasEmpresa(lector.getEmpresas().get(e).getCuentas());
						}
					
					}
					}catch(Exception e){e.printStackTrace(System.out);};
					return;
				}
			

	private Cuenta buscarCuenta(String s){//falta agregar que busque la fecha mas reciente
			try{
				ArrayList<Cuenta> listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa=new ArrayList<Cuenta>();
				Cuenta c=null;
				Date ultimaFecha=new Date();
				
			for(int i=0; i< this.getCuentasEmpresa().size();i++){
				if(this.getCuentasEmpresa().get(i).getNombre().equals(s)){
					
					listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa.add(this.getCuentasEmpresa().get(i));
				}
				}
			int i=0;
			while(i<listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa.size())	{
				ultimaFecha =formatoFecha.parse(listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa.get(i).getFecha());
				if((ultimaFecha.compareTo(formatoFecha.parse(listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa.get(i+1).getFecha())))>0){
					//ultimafecha seria la fecha mas reciente
					c=listaDeCuentasConIgualNombreDeCuentaDeUnaEmpresa.get(i);
					
				}
				i++;

			}
			return c;
			
				
			}catch(Exception e){e.printStackTrace(System.out);};
			
			return null;
			
		}
		private Indicador buscarIndicador(String s) {	
			try{
			for(int i=0; i< this.getIndicadores().size();i++){
				if(this.getIndicadores().get(i).getNombre().equals(s)){
					return this.getIndicadores().get(i);
				}
			}}catch(Exception e){e.printStackTrace(System.out);};
			return null;
		}
	
		//Getters and setters	
		public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
		public ArrayList<String> getOperaciones() {
			return operaciones;
		}

		public void setOperaciones(ArrayList<String> operaciones) {
			this.operaciones = operaciones;
		}
		public LeerArchivo getLector() {
			return lector;
		}

		public void setLector(LeerArchivo lector) {
			this.lector = lector;
		}

		public ArrayList<Cuenta> getCuentasEmpresa() {
			return cuentasEmpresa;
		}

		public void setCuentasEmpresa(ArrayList<Cuenta> cuentasEmpresa) {
			this.cuentasEmpresa = cuentasEmpresa;
		}

}