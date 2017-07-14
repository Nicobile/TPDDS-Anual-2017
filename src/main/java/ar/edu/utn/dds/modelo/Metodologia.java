package ar.edu.utn.dds.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.script.ScriptException;
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
				//sumarPuntosAPuntajesEmpresas(listaDeAplicarCondicion);

			}

		}

	}
/// esta anda bien faltaba validar algo pero dsps la voy a cambiar por una mas optima pq tiene for if que no hace nada PABLITO MIRA ESTO Y SE DESMAYA
	private void sumarPuntosAPuntajesEmpresas(ArrayList<PuntajeEmpresa> lista) throws NoSeEncuentraLaEmpresa {
		for (int i = 0; i < lista.size(); i++) {

			int j;
			
			j = this.buscarEmpresa(lista.get(i).getNombreEmpresa(), puntajeEmpresas);
			if (j==-1){}
			else{
			puntajeEmpresas.get(j).suma(buscarEmpresa(lista.get(i).getNombreEmpresa(), lista));}

		}
	}

	private void eliminarDeListaDePuntajesSiNoCumplioLaCondicion(ArrayList<PuntajeEmpresa> lista) {

		//lo comentado andaba barvaro, por ahora el remove if paso las pruebas 
		
		/*for (int j = 0; j < puntajeEmpresas.size(); j++) {
			PuntajeEmpresa e = puntajeEmpresas.get(j);
			
			
			  if ((lista.stream().filter(unaE ->
			  unaE.getNombreEmpresa().equals(e.getNombreEmpresa())).findFirst()
			 .isPresent())){ } 
			  else{
				 
				  puntajeEmpresas.remove(j);

					if (j == 0) {
						j = 0;
					} else {
						j = j - 1;
					}
			
		}

	}
	
	*/
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
		
		//Collections.reverse(puntajeEmpresas);
		if (puntajeEmpresas.isEmpty()) {
			System.out.println("No hay empresas que cumplan la metodologia aplicada");
		}
		return puntajeEmpresas;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static void main(String[] agrs) throws ScriptException, FileNotFoundException, IOException,
			NoSeEncuentraLaEmpresa, NoSeEncuentraElIndicador, NoSePudoOrdenarLaCondicion, NoSeEncuentraLaCuenta,
			NoSeEncuentraLaCuentaEnEsaFecha {
		Traductor t = new Traductor();

		LectorArchivo lector = new LectorArchivo(t);
		lector.leerArchivo("/home/dds/Desarrollo/workspace/2017-mn-group-12/src/test/resources/Datos.txt");
		ProcesarIndicadores procesador1 = new ProcesarIndicadores(t);
		procesador1.leerExcel("/home/dds/Desarrollo/workspace/2017-mn-group-12/src/test/resources/Indicadores.xls");

		Metodologia meto = new Metodologia("sada");
		Decreciente decre= new Decreciente(t.buscarIndicador("i_NivelDeuda"),t);
		Sumatoria sum=new Sumatoria(t.buscarIndicador("i_NivelDeuda"),t);
		Condicion cond2 = new Condicion(sum,7.0,">",2);	
		meto.agregarCondicion(cond2);
		Condicion condi = new Condicion(decre,2);
		
		meto.agregarCondicion(condi);		
	
		ArrayList<PuntajeEmpresa> empresas = meto.aplicarMetodologia();
		/*for(int i=0;i<empresas.size();i++){
			System.out.println(empresas.get(i).getNombreEmpresa());
		}*/
		
	
	
	
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
