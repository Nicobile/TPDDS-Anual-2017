package tp;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Persona {
	
 static ArrayList<LineaArchivo> lineasArchivo = new ArrayList<LineaArchivo>();
	   
	    public  void leerArchivo()  throws FileNotFoundException, IOException {
	       
	        String cadena;
	        BufferedReader b = new BufferedReader(new FileReader("/home/dds/Desarrollo/workspace/TP-DDS-2017/Datos.txt"));
	        int x;
	        String s;//donde voy guardando las palabrast 
	        String nomEmpresa=null,nomCuenta = null,valorCuenta=null;
	        LineaArchivo elementoColeccion;
	        
	      
	 
	       while((cadena= b.readLine())!=null) 
	        {	int numTokens = 0;
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
	 	        }
	 	        //para agregar los elementos a la lista
	 	       x=Integer.parseInt(valorCuenta);
		          
		          elementoColeccion=new LineaArchivo(nomEmpresa,nomCuenta,x);
		          lineasArchivo.add(elementoColeccion);
		          
	        }
	       
	        b.close();
	    }
		public ArrayList<LineaArchivo> getLineasArchivo() {
			return lineasArchivo;
		}
		public  void setLineasArchivo(ArrayList<LineaArchivo> lineasArchivo) {
			Persona.lineasArchivo = lineasArchivo;
		}

}
