package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

public class Metodologia {
	private ArrayList<Condicion> condicionesDeMetodologia = new ArrayList<Condicion>();
	private String nombre;
	private ArrayList<PuntajeEmpresa> puntajeEmpresas = new ArrayList<PuntajeEmpresa>();
	private int contador = 0;

	private int buscarEmpresa(String nomE, List<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresaException {

		for (int i = 0; i < lista.size(); i++) {

			if (lista.get(i).getNombreEmpresa().equals(nomE)) {
				return i;
			}
		}

		return -1;
	}

	private void aplicarCondicion(Condicion condicion) throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		List<PuntajeEmpresa> listaDeAplicarCondicion = new ArrayList<PuntajeEmpresa>();
		listaDeAplicarCondicion = condicion.aplicar();
		if (puntajeEmpresas.isEmpty()) {
			// lo del contador lo hago pq si no se cumple la primer condicion,
			// que es de filtrar, entonces la lista va a estar vacia
			// con lo cual al aplicar la siguiente condicion que , por ejemplo
			// la cumplen todas, va a convertir en todas las empresas
			// aun asi que no cumplan la primer condicion
			if (contador == 0) {
				contador++;
				for (int i = 0; i < listaDeAplicarCondicion.size(); i++) {
					puntajeEmpresas.add(listaDeAplicarCondicion.get(i));
					// puntajeEmpresas.get(i).suma(i); esto estaba mal pq sumaba
					// puntos aun cuando no habi que sumar...
				}
			} else {
				throw new NoHayEmpresasQueCumplanLaCondicionException("Ninguna empresa cumple con la condicion");
			}
		} else {
			if (!condicion.getFiltro()) {

				eliminarDeListaDePuntajesSiNoCumplioLaCondicion(puntajeEmpresas, listaDeAplicarCondicion);

				// de la lista que cumple la condicion elimino aquellos que no
				// estan en la lista de puntaje empresas
				// para sumar correctamente los puntajes, si no hago esto, al
				// ordenar de mayor a menor o viceversa
				// una empresa podria ocupar la posicion 3, pero en realidad las
				// primeras 2 empresas no estan en la lista de puntajes
				// con lo cual deberia sumar 1, que seria su posicion real ,
				// respecto a las que cumplen condicion en puntaje empresas, y
				// no 3

				eliminarDeListaDePuntajesSiNoCumplioLaCondicion(listaDeAplicarCondicion, puntajeEmpresas);

				sumarPuntosAPuntajesEmpresas(listaDeAplicarCondicion);

			} else {
				/* tengo que dejar solo quellas que cumplen la condicion */

				eliminarDeListaDePuntajesSiNoCumplioLaCondicion(puntajeEmpresas, listaDeAplicarCondicion);

			}

		}

	}
	// esta funcion suma los puntos a las empresas segun la posicion que ocupan
	// en la lista..... por eso trabajo con los for y si no encuentra la empresa
	// no hace nada pero no enci

	private void sumarPuntosAPuntajesEmpresas(List<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresaException {

		for (int i = 0; i < lista.size(); i++) {
			int j;
			j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);

			// si encuentra la empresa suma......
			try {
				puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(), lista));
			}
			// si no encuentra la empresa no hago nada, despues se elimina, ams
			// adelante
			catch (IndexOutOfBoundsException e) {
			}

		}
	}

	private void eliminarDeListaDePuntajesSiNoCumplioLaCondicion(List<PuntajeEmpresa> listaAremoverElementos,
			List<PuntajeEmpresa> listaAcomparar) {

		listaAremoverElementos
				.removeIf(unElementoDeMetodologia -> !listaAcomparar
						.stream().filter(unElementoQueCumpleUnaCondicion -> unElementoQueCumpleUnaCondicion
								.getNombreEmpresa().equals(unElementoDeMetodologia.getNombreEmpresa()))
						.findFirst().isPresent());

	}

	public ArrayList<PuntajeEmpresa> aplicarMetodologia() throws NoSeEncuentraLaEmpresaException, ScriptException,
			NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		Iterator<Condicion> condiciones = condicionesDeMetodologia.iterator();
		while (condiciones.hasNext()) {

			aplicarCondicion(condiciones.next());// aplico condicion

		}

		Collections.sort(puntajeEmpresas,
				(p1, p2) -> new Integer(p1.getPuntaje()).compareTo(new Integer(p2.getPuntaje())));

		if (puntajeEmpresas.isEmpty()) {
			throw new NoHayEmpresasQueCumplanLaCondicionException("No hay empresas que cumplan con la metodologia");
		}

		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarCondicion(Condicion cond) {
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
