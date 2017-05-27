package ar.edu.utn.dds.procesarArchivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import ar.edu.utn.dds.modelo.Traductor;
import excepciones.NoSeEncuentraEnLaLista;

public class LectorArchivo {
	private Traductor t = new Traductor();

	public LectorArchivo(Traductor t) {
		super();
		this.t = t;
	}

	static ArrayList<LineaArchivo> lineasArchivo = new ArrayList<LineaArchivo>();

	public void leerArchivo(String archivo) throws FileNotFoundException, IOException, NoSeEncuentraEnLaLista {

		BufferedReader b = new BufferedReader(new FileReader(archivo));

		String cadena;
		String s;// donde voy guardando las palabrast
		String nomEmpresa = null, nomCuenta = null, valorCuenta = null, fecha = null;
		LineaArchivo elementoColeccion;

		while ((cadena = b.readLine()) != null) {
			int numTokens = 0;
			StringTokenizer cad = new StringTokenizer(cadena);
			// bucle por todas las palabras y guardo las palabras
			while (cad.hasMoreTokens()) {
				s = cad.nextToken();
				numTokens++;
				if (numTokens == 1) {
					nomEmpresa = s;
				}
				if (numTokens == 2) {
					nomCuenta = s;
				}
				if (numTokens == 3) {
					valorCuenta = s;
				}
				if (numTokens == 4) {
					fecha = s;
				}
			}

			elementoColeccion = new LineaArchivo(nomEmpresa, nomCuenta, Integer.parseInt(valorCuenta), fecha);
			lineasArchivo.add(elementoColeccion);

		}

		b.close();

		// paso lista a empresa
		t.armarListaEmpresas(lineasArchivo);

	}

	public ArrayList<LineaArchivo> getLineasArchivo() {
		return lineasArchivo;
	}

	public void setLineasArchivo(ArrayList<LineaArchivo> lineasArchivo) {
		LectorArchivo.lineasArchivo = lineasArchivo;
	}

}
