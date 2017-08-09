package ar.edu.utn.dds.interfazGrafica;

import java.util.ArrayList;

public class Archivos {
	
	public ArrayList<String> listaArchivos = new ArrayList<String>();

	public void agregarArchivo(String archivo) {
		listaArchivos.add(archivo);
	}

	public boolean buscarArchivo(String archivo) {
		return listaArchivos.contains(archivo);
	}

	public ArrayList<String> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(ArrayList<String> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
}
