package ar.edu.utn.dds.PROCESARARCHIVO;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {
	private LeerArchivo lector;//me permite conocer a la lista de empresas para buscar sobre la cual estoy calculando
	public ProcesarIndicadores(LeerArchivo lector) {
		super();
		this.lector = lector;
	}
	//public ArrayList<Cuenta> cuentasEmpresa=new ArrayList<Cuenta>();//de la empresa sobre la que el indicador esta trabajando, me guardo todas sus cuentas mas recientes para utilizarla para calcular el indicador
	// public ArrayList<String> operaciones =new ArrayList<String>();//operaciones es una lista que va descomponiendo la operacion de los indicadores hasta llegar a un ponto en donde son todos cuentas y operadores
	public ArrayList<Indicador> indicadores = new ArrayList<Indicador>();//los indicadores sobre los que tengo que operar 1 por 1
	//SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");//declaro el formato de la fecha
    
	   
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
                   
                  
                  
                   if(type== CellType.LABEL){
                	   Indicador indicador= new Indicador(cell.getContents(),cell2.getContents(),cell3.getContents());
                	   //seteo el nombre del indicador
                	   indicadores.add(indicador);
                        
                        }}
                        
                } catch (BiffException e){e.printStackTrace();}
      
        }
	
	public void cargarIndPredefinidos(String nombre, String operacion,String nombreDeEmpresa){
		Indicador indicador=new Indicador(nombre,operacion,nombreDeEmpresa);
		indicadores.add(indicador);
	 }
       	
 public Empresa buscarEmpresaSobreLaQueIndicadorSeCalcula(Indicador ind){
	 //busco en la lista de empresas el nombre de la empresadel indicador
	 
	 
		 for (int i=0;i<lector.getEmpresas().size();i++){
	 
		 if(lector.getEmpresas().get(i).getNombre().equals(ind.getNombreEmpresa()))
		 {
			 return lector.getEmpresas().get(i);
		 }
	 }
	 return null;
 }
public ArrayList<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e){
	
	return e.getCuentas();
}
public ArrayList<Cuenta> obtenerSoloLasCuentasMasRecientesDeEmpresa (Empresa e) throws ParseException{//carga a cuentasEmpresa
	ArrayList<Cuenta> c=this.buscarDeUnaEmpresaTodasSusCuentas(e);
	ArrayList<Cuenta> cuentasEmpresa=new ArrayList<Cuenta>();
	for(int i=0;i<c.size();i++){
		int x=existeLaCuenta(c.get(i),cuentasEmpresa);//existe la cuenta devuelve el indice donde se la encontro
		if(x<0){//no existe la cuenta en la lista
			cuentasEmpresa.add(c.get(i));
		}
		/*else{
			Date ultimaFecha =formatoFecha.parse(c.get(i).getFecha());// de la cuenta de la lista no filtrada por ultimas cuentas de empresa obtengo la fecha
		
			if((ultimaFecha.compareTo(formatoFecha.parse(cuentasEmpresa.get(x).getFecha())))>0){//comparo la fecha de la cuenta con la que encontre en la lista de las ultimas cuentas de la empresa
				//ultimafecha seria la fecha mas reciente,es decir seria la que esta en la lista no filtrada
				
				cuentasEmpresa.set(x, c.get(i));// reemplazo ne la misma posicion la cuenta pq la que encontre erra mas reciente que la que estaba
		}
	}*/}
	return cuentasEmpresa;
	}
private int existeLaCuenta(Cuenta c, ArrayList<Cuenta> cuentasEmpresa){
	for(int i=0;i<cuentasEmpresa.size();i++){
		if(cuentasEmpresa.get(i).getNombre().equals(c.getNombre())){
			return i;//retorno la posicion donde encuentro la empresa
		}
	}
	return -1;
	
}
private Cuenta buscarCuenta(String nombreCuenta, ArrayList<Cuenta> cuentas){
	
		for (int i=0;i<cuentas.size();i++){
	
		if(cuentas.get(i).getNombre().equals(nombreCuenta)){
			return cuentas.get(i);
		}
	}
	return null;
}

public int calcularIndicador(Indicador i,LeerArchivo lector) throws ParseException{
	ArrayList<Cuenta> cuentasMasRecientesEmpresa=new ArrayList<Cuenta>();
	ArrayList<String> listaOperacionDelIndicador=new ArrayList<String>();
	cuentasMasRecientesEmpresa=this.obtenerSoloLasCuentasMasRecientesDeEmpresa(this.buscarEmpresaSobreLaQueIndicadorSeCalcula(i));//a cuentasEmpresa le agrego solo las cuentas de la empresa sobre la que calculo el indicador
	listaOperacionDelIndicador=this.descomponerString(i.getOperacion(),cuentasMasRecientesEmpresa);
	return i.operar(this.armarExpresion(listaOperacionDelIndicador));
}
public ArrayList<String> descomponerString(String operaciones,ArrayList<Cuenta> cuentas){ //descompongo la operacion de un indicador y agrego a la lista el valor de la cuenta
	ArrayList<String> operacionDeIndicadorDescompuesta=new ArrayList<String>();
	char[] c=operaciones.toCharArray();
	StringBuilder s= new StringBuilder();
	
	
	for(int i=0;i<c.length;i++){
		
		if (c[i] =='+' ||c[i] == '-' ||c[i] =='(' ||c[i] == ')' ||c[i] =='*' ||c[i] == '/'  ){
			operacionDeIndicadorDescompuesta.add(Character.toString(c[i]));}
			
		else {
			s.append(c[i]);
			}
		
		//	if(s.length()>0){
				//lista.add(s.toString());
			/*	Indicador ind=this.buscarIndicador(s.toString());
				if(ind!=null){
					String x=new String();
							x=ind.getOperacion();
					//getOperaciones().add(x);//agrego una operacion
					this.descomponerString(x);
					
				}*/
				
				
				Cuenta cue=this.buscarCuenta(s.toString(),cuentas);
				if(cue!=null){
					operacionDeIndicadorDescompuesta.add(String.valueOf(cue.getValor()));
					s.setLength(0);
					}
				
					//operacionDeIndicadorDescompuesta.add(Integer.toString(cue.getValor()));
					//operacionDeIndicadorDescompuesta.add(s.toString());
					
					
				}															
				
			//}
	return operacionDeIndicadorDescompuesta;
		
		}
		/*	
		else {
			s.append(c[i]);
		    						
		}*/
	
	
private String armarExpresion(ArrayList<String> listaDeOperacion) {
	String s=new String();
	for(int i=0;i<listaDeOperacion.size();i++){
		s=s.concat(" ").concat(listaDeOperacion.get(i));
	}
	return s;
}
	
		//Getters and setters	
		public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	 public static void main(String[] args) throws IOException, ParseException {
		 LeerArchivo lector=new LeerArchivo();
		 lector.leerArchivo();
		 ProcesarIndicadores p=new ProcesarIndicadores(lector);
		 p.leerExcel();
		 ExpressionParser e=new ExpressionParser();
		 ArrayList<String> lista=new ArrayList<>();
		 ArrayList<Cuenta> cuentas= new ArrayList<>();
		 Cuenta cuentaA=new Cuenta("cuentaA", 12, "12/10/1696");
		 Cuenta cuentaB=new Cuenta("cuentaB", 122, "12/10/1696");
		 Cuenta cuentaC=new Cuenta("cuentaC", 182, "12/10/1696");
		 Cuenta cuentaD=new Cuenta("cuentaD", 13, "12/10/1696");
		 cuentas.add(cuentaA);
		 cuentas.add(cuentaB);
		 cuentas.add(cuentaC);
		 cuentas.add(cuentaD);
		 Indicador i=new Indicador("Indicador1", "(cuentaB-cuentaC-cuentaA)*cuentaD", "Facebook");
		 lista=p.descomponerString(i.getOperacion(),cuentas);
		/* for(int j=0;j<lista.size();j++){
		 System.out.print(lista.get(j));
		 }*/
		 //System.out.print(p.armarExpresion(lista));
		 System.out.print(e.parse(p.armarExpresion(lista)));
		 
		
		 
				 
 }

		
		
	

}