package ar.edu.utn.dds.procesarArchivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Traductor;

public class LectorArchivo {

	private Traductor t = new Traductor();
	static ArrayList<LineaArchivo> lineasArchivo = new ArrayList<LineaArchivo>();

	public LectorArchivo(Traductor t) {
		super();
		this.t = t;
	}

	public void leerArchivo(String archivo) throws FileNotFoundException, IOException, NoSeEncuentraLaEmpresaException {

		BufferedReader b = new BufferedReader(new FileReader(archivo));

		String s; /* donde voy guardando las palabrast */
		String cadena;
		LineaArchivo elementoColeccion;

		String nomEmpresa = null, nomCuenta = null, valorCuenta = null, fechaInicio = null, fechaFin = null,
				fechaInscripcion = null;

		while ((cadena = b.readLine()) != null) {
			int numTokens = 0;
			StringTokenizer cad = new StringTokenizer(cadena);

			/* bucle por todas las palabras y guardo las palabras */
			while (cad.hasMoreTokens()) {
				s = cad.nextToken();
				numTokens++;
				if (numTokens == 1) {
					nomEmpresa = s;
				}
				if (numTokens == 2) {
					fechaInscripcion = s;
				}
				if (numTokens == 3) {
					nomCuenta = s;
				}
				if (numTokens == 4) {
					valorCuenta = s;
				}
				if (numTokens == 5) {
					fechaInicio = s;
				}
				if (numTokens == 6) {
					fechaFin = s;
				}
			}

			elementoColeccion = new LineaArchivo(nomEmpresa, fechaInscripcion, nomCuenta, Integer.parseInt(valorCuenta),
					fechaInicio, fechaFin);
			lineasArchivo.add(elementoColeccion);
		}
		b.close();

		/* paso lista a empresa */
		t.armarListaEmpresas(lineasArchivo);
	}

	public ArrayList<LineaArchivo> getLineasArchivo() {
		return lineasArchivo;
	}

	public void setLineasArchivo(ArrayList<LineaArchivo> lineasArchivo) {
		LectorArchivo.lineasArchivo = lineasArchivo;
	}

}
