package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
		throw new NoSeEncuentraLaEmpresa("No se encontro la empresa especificada");
	}

	private void aplicarCondicion(Condicion condicion)
			throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador { /*
											 * aplicar condicion modifica la
											 * lista de puntaje empresas ,lista
											 * es una lista vacia que carga los
											 * datos de una unica condicion y
											 * con lista puedo modificar la
											 * lista de puntaje empresas
											 */
		ArrayList<PuntajeEmpresa> lista = new ArrayList<PuntajeEmpresa>();
		lista = condicion.aplicar();
		if (puntajeEmpresas == null) {
			for (int i = 0; i < lista.size(); i++) {
				puntajeEmpresas.add(lista.get(i));

			}
		} else {
			if(!condicion.getFiltro()){
			for (int i = 0; i < lista.size(); i++) {

				int j;
				j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);
				
				puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(),
						lista));/*
								 * sumo a lista puntaje empresas la posicion que
								 * ocupa la empresa en la lista de condicion
								 */
			}}
			else{
				/*tengo que filtrar solo quellas que cumplen la condicion*/
				for (int i=0;i<lista.size();i++){
					PuntajeEmpresa elementoLista=lista.get(i);
					puntajeEmpresas.stream().filter(unaE -> unaE.getNombreEmpresa().equals(elementoLista.getNombreEmpresa()));
					
				}
				
			}

		}

	}

	public ArrayList<PuntajeEmpresa> aplicarMetodologia()
			throws NoSeEncuentraLaEmpresa, ScriptException, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador { /*
											 * deberia haber una clase que tenga
											 * una lista de metologias
											 */
		/*
		 * plicaria todas las condiciones y una vez terminado sumo los puntajes
		 * de empresa y muestro la lista oredanda
		 */
		Iterator<Condicion> condiciones = condicionesDeMetodologia.iterator();
		while (condiciones.hasNext()) {
			this.aplicarCondicion(condiciones.next());// aplico condicion

		}
		/*
		 * ahora aplique toda las condiciones sobre la lista puntejeempresas
		 * ahora debo ordenarlas
		 */

		Collections.sort(puntajeEmpresas, (p1, p2) -> new Integer(p1.getPuntaje()).compareTo(new Integer(p2.getPuntaje())));
		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	// prueba del Collection anda bien
	
	public static void main(String[] agrs) throws ScriptException{ ArrayList<PuntajeEmpresa>
	 puntajeEmpresas = new ArrayList<PuntajeEmpresa>(); PuntajeEmpresa p1=new
	  PuntajeEmpresa(); p1.setResultadoDeAplicarCondicion(8);
	  
	  PuntajeEmpresa p2=new PuntajeEmpresa(); p2.setResultadoDeAplicarCondicion(7); PuntajeEmpresa
	  p3=new PuntajeEmpresa(); p3.setResultadoDeAplicarCondicion(5);; PuntajeEmpresa p4=new
	  PuntajeEmpresa(); p4.setResultadoDeAplicarCondicion(8);; PuntajeEmpresa p5=new PuntajeEmpresa();
	  p5.setResultadoDeAplicarCondicion(10);; PuntajeEmpresa p6=new PuntajeEmpresa(); p6.setResultadoDeAplicarCondicion(0);
	  puntajeEmpresas.add(p1); puntajeEmpresas.add(p2);
	  puntajeEmpresas.add(p3); puntajeEmpresas.add(p4);
	  puntajeEmpresas.add(p5); puntajeEmpresas.add(p6);
	  Collections.sort(puntajeEmpresas, (pA, pB) -> new Double(pA.getResultadoDeAplicarCondicion()).compareTo(new Double(pB.getResultadoDeAplicarCondicion()))); 
	  for (int i=0;i<puntajeEmpresas.size();i++){
	  System.out.println(puntajeEmpresas.get(i).getResultadoDeAplicarCondicion()); }
	 
	  System.out.println(puntajeEmpresas.size());
	  
	 
	Collections.reverse(puntajeEmpresas);
	 
	  for (int i=0;i<puntajeEmpresas.size();i++){
		  System.out.println(puntajeEmpresas.get(i).getResultadoDeAplicarCondicion()); }
	  
		  System.out.println(puntajeEmpresas.size());
		
		 }
	
}
