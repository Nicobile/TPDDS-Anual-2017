package tp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
public class Persona {
	
	  static Collection<LineaArchivo> lineasArchivo;
	   
	
	    private static void recorrerlinea(String cadena,int a, StringBuffer b){
	    	  while (cadena.charAt(a)!=' ') {
	        	  b.append(cadena.charAt(a));
	        	  a++;
	        	  return;
	          }
	    	
	    }
	    public static void leerArchivo()  throws FileNotFoundException, IOException {
	       
	        String cadena;
	        
	        File archivo=new File ("/home/dds/Desarrollo/workspace/TP-DDS-2017/Datos");
	        FileReader f = new FileReader(archivo);
	        BufferedReader b = new BufferedReader(f);
	        int a=0,x;
	        StringBuffer nombreEmpresa = new StringBuffer();
	        StringBuffer nombreCuenta = new StringBuffer();
	        StringBuffer valorCuenta = new StringBuffer();
	        while((cadena= b.readLine())!=null) {
	        	
	        	
	          recorrerlinea(cadena,a,nombreEmpresa);
	          a++;
	          recorrerlinea(cadena,a,nombreCuenta);
	          a++;
	          while (cadena.charAt(a)!='\n') {
	        	  valorCuenta.append(cadena.charAt(a));
	        	  a++;
	          }
	          x=Integer.parseInt(valorCuenta.toString());
	          LineaArchivo elementoColeccion;
	          elementoColeccion=new LineaArchivo(nombreEmpresa.toString(),nombreCuenta.toString(),x);
	          lineasArchivo.add(elementoColeccion);
	          a=0;	          	          
	        }
	        b.close();
	    }
		public static Collection<LineaArchivo> getLineasArchivo() {
			return lineasArchivo;
		}
		public static void setLineasArchivo(Collection<LineaArchivo> lineasArchivo) {
			Persona.lineasArchivo = lineasArchivo;
		}

}
