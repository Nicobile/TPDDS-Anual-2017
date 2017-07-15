package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicion;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicion;


public class Metodologia {
	private ArrayList<Condicion> condicionesDeMetodologia = new ArrayList<Condicion>();
	private String nombre;
	private ArrayList<PuntajeEmpresa> puntajeEmpresas = new ArrayList<PuntajeEmpresa>();

	private int buscarEmpresa(String nomE, ArrayList<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresa {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getNombreEmpresa().equals(nomE)) {
				return i;
			}
		}
		
		return -1;
	}

	private void aplicarCondicion(Condicion condicion)
			throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		ArrayList<PuntajeEmpresa> listaDeAplicarCondicion = new ArrayList<PuntajeEmpresa>();
		listaDeAplicarCondicion = condicion.aplicar();
		if (puntajeEmpresas.isEmpty()) {
			for (int i = 0; i < listaDeAplicarCondicion.size(); i++) {
				puntajeEmpresas.add(listaDeAplicarCondicion.get(i));
				puntajeEmpresas.get(i).suma(i);

			}
		} else {
			if (!condicion.getFiltro()) {
				
				eliminarDeListaDePuntajesSiNoCumplioLaCondicion(listaDeAplicarCondicion);
				
				sumarPuntosAPuntajesEmpresas(listaDeAplicarCondicion);
				
			} else {
				/* tengo que dejar solo quellas que cumplen la condicion */
				
				eliminarDeListaDePuntajesSiNoCumplioLaCondicion(listaDeAplicarCondicion);
		

			}

		}

	}

	private void sumarPuntosAPuntajesEmpresas(ArrayList<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresa {
		for (int i = 0; i < lista.size(); i++) {
			int j;			
			j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);
			if ( j!=-1){
			
			puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(), lista));}

		}
	}

	private void eliminarDeListaDePuntajesSiNoCumplioLaCondicion(ArrayList<PuntajeEmpresa> lista) {

		puntajeEmpresas.removeIf(unElementoDeMetodologia-> ! lista.stream().filter(unElementoQueCumpleUnaCondicion->unElementoQueCumpleUnaCondicion.getNombreEmpresa().equals(unElementoDeMetodologia.getNombreEmpresa())).findFirst().isPresent());
		
	
	}

	public ArrayList<PuntajeEmpresa> aplicarMetodologia()
			throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		Iterator<Condicion> condiciones = condicionesDeMetodologia.iterator();
		while (condiciones.hasNext()) {
			
			this.aplicarCondicion(condiciones.next());// aplico condicion

		}

		Collections.sort(puntajeEmpresas,
				(p1, p2) -> new Integer(p1.getPuntaje()).compareTo(new Integer(p2.getPuntaje())));
		
		if (puntajeEmpresas.isEmpty()) {
			throw new NoHayEmpresasQueCumplanLaCondicion("No hay empresas que cumplan con la metodologia");
		}
		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	public void agregarCondicion(Condicion cond){
		this.getCondicionesDeMetodologia().add(cond);
	}

	public ArrayList<Condicion> getCondicionesDeMetodologia() {
		return condicionesDeMetodologia;
	}

	public void setCondicionesDeMetodologia(ArrayList<Condicion> condicionesDeMetodologia) {
		this.condicionesDeMetodologia = condicionesDeMetodologia;
	}

	public ArrayList<PuntajeEmpresa> getPuntajeEmpresas() {
		return puntajeEmpresas;
	}

	public void setPuntajeEmpresas(ArrayList<PuntajeEmpresa> puntajeEmpresas) {
		this.puntajeEmpresas = puntajeEmpresas;
	}

	public Metodologia(String nombre) {
		super();
		this.nombre = nombre;
	}

}
