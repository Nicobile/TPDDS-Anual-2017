package ar.edu.utn.dds.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicion;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;

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

	private void aplicarCondicion(Condicion condicion) throws NoSeEncuentraLaEmpresa, ScriptException,
			NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador { 
		ArrayList<PuntajeEmpresa> lista = new ArrayList<PuntajeEmpresa>();
		lista = condicion.aplicar();
		if (puntajeEmpresas.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {
				puntajeEmpresas.add(lista.get(i));
				puntajeEmpresas.get(i).suma(i);

			}
		} else {
			if (!condicion.getFiltro()) {
				for (int i = 0; i < lista.size(); i++) {
					PuntajeEmpresa e=lista.get(i);
					puntajeEmpresas.stream().filter(unaE -> unaE.getNombreEmpresa().equals(e.getNombreEmpresa()));

				}
				for (int i = 0; i < lista.size(); i++) {

					int j;
					j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);

					puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(),lista));
					
				}
			} else {
				/* tengo que filtrar solo quellas que cumplen la condicion */
				for (int i = 0; i < lista.size(); i++) {
					PuntajeEmpresa elementoLista = lista.get(i);
					puntajeEmpresas.stream()
							.filter(unaE -> unaE.getNombreEmpresa().equals(elementoLista.getNombreEmpresa()));

				}

			}

		}

	}

	public ArrayList<PuntajeEmpresa> aplicarMetodologia() throws NoSeEncuentraLaEmpresa, ScriptException,
			NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador { /*
										 * deberia haber una clase que tenga una
										 * lista de metologias
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

		Collections.sort(puntajeEmpresas,
				(p1, p2) -> new Integer(p1.getPuntaje()).compareTo(new Integer(p2.getPuntaje())));
		Collections.reverse(puntajeEmpresas);
		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public static void main(String[] agrs) throws ScriptException, FileNotFoundException, IOException, NoSeEncuentraLaEmpresa, NoSeEncuentraElIndicador, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {
		Traductor t = new Traductor();
		ExpressionParser parser=new ExpressionParser();
		LectorArchivo lector = new LectorArchivo(t);
		lector.leerArchivo("/home/dds/Desarrollo/workspace/2017-mn-group-12/src/test/resources/Datos.txt");
		ProcesarIndicadores procesador1 = new ProcesarIndicadores(t);
		procesador1.leerExcel("/home/dds/Desarrollo/workspace/2017-mn-group-12/src/test/resources/Indicadores.xls");

		Metodologia meto = new Metodologia("BUFO");
		
		Promedio prom = new Promedio(t.buscarIndicador("i_NivelDeuda"),t);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond1 = new Condicion(prom,2,"mayor");
		Condicion cond2 = new Condicion(sum,7.0,">",2);
		meto.getCondicionesDeMetodologia().add(cond1);
		meto.getCondicionesDeMetodologia().add(cond2);
	 	ArrayList<PuntajeEmpresa> listin = meto.aplicarMetodologia();
		
	 	//sumatoria anda el problema eesta en condicion
		//ArrayList<PuntajeEmpresa> listin=prom.calcularValor(1);
		
	
		ArrayList<PuntajeEmpresa> listin2=prom.calcularValor(2);
		ArrayList<PuntajeEmpresa> listin3=sum.calcularValor(2);
		
		for (int i=0;i<listin.size();i++){
		System.out.println(listin.get(i).getNombreEmpresa());
		System.out.println(listin.get(i).getResultadoDeAplicarCondicion());
		System.out.println(listin.get(i).getPuntaje());
		}
		for (int i=0;i<listin2.size();i++){
			System.out.println(listin2.get(i).getNombreEmpresa());
			System.out.println(listin2.get(i).getResultadoDeAplicarCondicion());
			System.out.println(listin2.get(i).getPuntaje());
			}
		for (int i=0;i<listin3.size();i++){
			System.out.println(listin3.get(i).getNombreEmpresa());
			System.out.println(listin3.get(i).getResultadoDeAplicarCondicion());
			System.out.println(listin3.get(i).getPuntaje());
			}
		
		
		
		
		/*ArrayList<PuntajeEmpresa> puntajeEmpresas = new ArrayList<PuntajeEmpresa>();
		
		ArrayList<PuntajeEmpresa> lista = new ArrayList<PuntajeEmpresa>();
		lista = cond1.aplicar();
		if (puntajeEmpresas.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {
				puntajeEmpresas.add(lista.get(i));

			}
		} else {
			if (!cond1.getFiltro()) {
				for (int i = 0; i < lista.size(); i++) {
					puntajeEmpresas.stream().filter(unaE -> unaE.getNombreEmpresa().equals(unaE.getNombreEmpresa()));

				}

				for (int i = 0; i < lista.size(); i++) {

					int j;
					j =meto.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);

					puntajeEmpresas.get(j).suma(meto.buscarEmpresa(lista.get(i).getNombreEmpresa(),
							lista));
				}
			} else {
				
				for (int i = 0; i < lista.size(); i++) {
					PuntajeEmpresa elementoLista = lista.get(i);
					puntajeEmpresas.stream()
							.filter(unaE -> unaE.getNombreEmpresa().equals(elementoLista.getNombreEmpresa()));

				}

			}

		}
		for(int i=0;i<puntajeEmpresas.size();i++){
			System.out.println(puntajeEmpresas.get(i).getResultadoDeAplicarCondicion());
		}*/
		


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
