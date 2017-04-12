package tp;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LeerArchivo {
	

		Empresa empresa;
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
		        empresa.armarListaEmpresas(lineasArchivo);
		       }
			public ArrayList<LineaArchivo> getLineasArchivo() {
				return lineasArchivo;
			}
			public  void setLineasArchivo(ArrayList<LineaArchivo> lineasArchivo) {
				LeerArchivo.lineasArchivo = lineasArchivo;
			}

	}

