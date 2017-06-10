package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public class Metodologia {
	private ArrayList<Condicion> condicionesDeMetodologia = new ArrayList<Condicion>();
	private String nombre;
	ArrayList<PuntajeEmpresa> puntajeEmpresas = new ArrayList<PuntajeEmpresa>();

	private int buscarEmpresa(String nomE, ArrayList<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresa {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getNombreEmpresa().equals(nomE)) {
				return i;
			}
		}
		throw new NoSeEncuentraLaEmpresa("No se encontro la empresa especificada");
	}

	private void aplicarCondicion(Condicion condicion)
			throws NoSeEncuentraLaEmpresa { /*
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
			for (int i = 0; i < lista.size(); i++) {

				int j;
				j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);
				puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(),
						lista));/*
								 * sumo a lista puntaje empresas la posicion que
								 * ocupa la empresa en la lista de condicion
								 */
			}

		}

	}

	public ArrayList<PuntajeEmpresa> aplicarMetodologia()
			throws NoSeEncuentraLaEmpresa { /*
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

		Collections.sort(puntajeEmpresas, (p1, p2) -> new Integer(p1.getValor()).compareTo(new Integer(p2.getValor())));
		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	// prueba del Collection anda bien
	/*
	 * public static void main(String[] agrs){ ArrayList<PuntajeEmpresa>
	 * puntajeEmpresas = new ArrayList<PuntajeEmpresa>(); PuntajeEmpresa p1=new
	 * PuntajeEmpresa(); p1.setValor(8);
	 * 
	 * PuntajeEmpresa p2=new PuntajeEmpresa(); p2.setValor(7); PuntajeEmpresa
	 * p3=new PuntajeEmpresa(); p3.setValor(9); PuntajeEmpresa p4=new
	 * PuntajeEmpresa(); p4.setValor(1); PuntajeEmpresa p5=new PuntajeEmpresa();
	 * p5.setValor(0); PuntajeEmpresa p6=new PuntajeEmpresa(); p6.setValor(0);
	 * puntajeEmpresas.add(p1); puntajeEmpresas.add(p2);
	 * puntajeEmpresas.add(p3); puntajeEmpresas.add(p4);
	 * puntajeEmpresas.add(p5); puntajeEmpresas.add(p6);
	 * Collections.sort(puntajeEmpresas, (pA, pB) -> new
	 * Integer(pA.getValor()).compareTo(new Integer(pB.getValor()))); for (int
	 * i=0;i<puntajeEmpresas.size();i++){
	 * System.out.println(puntajeEmpresas.get(i).getValor()); }
	 * System.out.println(puntajeEmpresas.size());
	 * 
	 * 
	 * 
	 * 
	 * }
	 */


}
