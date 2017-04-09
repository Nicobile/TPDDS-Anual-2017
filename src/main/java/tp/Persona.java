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
	        int numTokens = 0,x;
	        String s;
	        String nomEmpresa=null,nomCuenta = null,valorCuenta=null;
	        while((cadena= b.readLine())!=null) {	
	 	        StringTokenizer cad = new StringTokenizer (cadena);
	 	        
	 	        // bucle por todas las palabras
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
	 	       x=Integer.parseInt(valorCuenta);
		          LineaArchivo elementoColeccion;
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
