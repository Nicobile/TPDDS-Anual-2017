package tp;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LeerArchivo {
	

	//creo lista empresas
	ArrayList<Empresa> empresas = new ArrayList<Empresa>();
	 static ArrayList<LineaArchivo> lineasArchivo = new ArrayList<LineaArchivo>();
		   
		    public  void leerArchivo()  throws FileNotFoundException, IOException {
		       
		        String cadena;
		        BufferedReader b = new BufferedReader(new FileReader("/home/dds/Desarrollo/workspace/TP-DDS-2017/Datos.txt"));
		        //int x;
		        String s;//donde voy guardando las palabrast 
		        String nomEmpresa=null,nomCuenta = null,valorCuenta=null, fecha=null;
		        LineaArchivo elementoColeccion;
		        
		      
		 
		       while((cadena= b.readLine())!=null) 
		        {	
		    	   int numTokens = 0;
		 	        StringTokenizer cad = new StringTokenizer (cadena);	 	        
		 	        // bucle por todas las palabras y guardo las palabras
		 	        while (cad.hasMoreTokens())
		 	        {
		 	            s = cad.nextToken();
		 	            numTokens++;
		 	            if(numTokens==1){
		 	            	nomEmpresa=s;
		 	            }
		 	            if (numTokens==2){
		 	            	nomCuenta=s;
		 	            }
		 	           if (numTokens==3){
		 	            	valorCuenta=s;
		 	            }
		 	          if (numTokens==4){
		 	            	fecha=s;
		 	            }
		 	        }
		 	        //para agregar los elementos a la lista
		 	       //x=Integer.parseInt(valorCuenta);
			          
			          elementoColeccion=new LineaArchivo(nomEmpresa,nomCuenta,Integer.parseInt(valorCuenta),fecha);
			          lineasArchivo.add(elementoColeccion);
			          //this.empresa.inicializate(elementoColeccion);
			          //this.cuenta.inicializate(elementoColeccion);
			           
			          
			          
		        }
		       
		        b.close();
		        
		        //paso lista a empresa
		        this.armarListaEmpresas(lineasArchivo);
		       }
		    public ArrayList<Empresa> getEmpresas() {
				return empresas;
			}
			public void setEmpresas(ArrayList<Empresa> empresas) {
				this.empresas = empresas;
			}
			private int buscarEnLista(ArrayList<Empresa> empresas, String nombreEmpresa ){
				for(int x=0;x<empresas.size();x++) {
					  //pregunto si ya existe la empresa
					if(empresas.get(x).getNombre()==nombreEmpresa){
					return x;	
					}
					
					}
				return -1;
			}
			
			public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo ){
				
				
				
				//recorro la lista que contiene todos los datos
				for(int x=0;x<lineasArchivo.size();x++) {
					
					int i;//indice de donde encuentra el elemento en la lista de empresas ya existentes
					
					i=buscarEnLista(empresas,lineasArchivo.get(x).nombreEmpresa);  
					//pregunto si ya existe la empresa
					
					if((i>=0)){//si ya existe la empresa 
						//creo una nueva cuenta
						Cuenta cuenta=new Cuenta(lineasArchivo.get(x).nombreCuenta,lineasArchivo.get(x).valorCuenta,lineasArchivo.get(x).fecha);//tomo los elementos del original y creo una cuenta para agregar a la lista
						
						empresas.get(i).getCuentas().add(cuenta);//agrego la cuenta, en la lista de cuentas, de le empresa ya existente
					}
					//la empresa no existia entonces la creo
					else{
						ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
						Empresa empresa= new Empresa(lineasArchivo.get(x).nombreEmpresa,cuentas);
						//creo la cuenta de la nueva empresa
						Cuenta cuenta=new Cuenta(lineasArchivo.get(x).nombreCuenta,lineasArchivo.get(x).valorCuenta,lineasArchivo.get(x).fecha);												
						empresas.add(empresa);//agrego la empresa a la lista de empresas
						
						empresa.getCuentas().add(cuenta);
						
					}
					}
				
			}
				
			public ArrayList<LineaArchivo> getLineasArchivo() {
				return lineasArchivo;
			}
			public  void setLineasArchivo(ArrayList<LineaArchivo> lineasArchivo) {
				LeerArchivo.lineasArchivo = lineasArchivo;
			}

	}

