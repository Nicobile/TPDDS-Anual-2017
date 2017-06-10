package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.Collections;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public abstract class Condicion {
	private Traductor traductor=new Traductor();
	private LadoIzq ladoIzq;
	private Double ladoDer=null;/* lado der SIEMPRE va a ser un numero PUEDE NO INGRESARSE*/
	private String comparador=null;/*PUEDE NO INGRESARSE*/ 
	private int periodos;
	private String criterio;//mayor o menor ingreesa en la interfaz grafica
	/*2 formas de ingresar por interfaz
	 * lado izquierdo derecho y comparador y periodos
	 * lado izquierdo y criterio*/

	public ArrayList<PuntajeEmpresa>  aplicar() throws ScriptException{/* ordena a las empresas segun cumplan la condicion
	*/
		/*por cada condicion clono la lista  de empresas del traductor
		para no modificar la posiciones de la lista de empresa del traductor*/
		
		ArrayList<Empresa> empresas= (ArrayList<Empresa>) traductor.getEmpresas().clone();
		ArrayList<PuntajeEmpresa> valoresAizq=new ArrayList<PuntajeEmpresa>();/* debe a partir de la lista de empresas crear esta estructura y oredenarla segun 
		corresponda por el valor del campo valor*/
		
		valoresAizq=ladoIzq.calcularValor(empresas,periodos);/*esto va a aplicar a todas las empresas el lado izq
		y los guarda en la lista SIN ORDENAR*/
		if((ladoDer==null && comparador!=null) || (comparador==null && ladoDer!=null)){
			System.out.println("Error");/*dsps crear el error*/
		}
		if(ladoDer==null && comparador==null){
			/*solo hay lado izquierdo, entonces ordeno la lista que me devuelve lado izquierdo y ahi estaria la condicion aplicada*/
			if(criterio.equals("mayor")){
				/*ordena de menor a mayor*/
			 Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion()).compareTo(new Double(v2.getResultadoDeAplicarCondicion())));
			 //ordeno de mayor a menor
			 Collections.reverse(valoresAizq);
			 return valoresAizq;
			}
			else{
				// de menor a mayor
				Collections.sort(valoresAizq, (v1, v2) -> new Double(v1.getResultadoDeAplicarCondicion()).compareTo(new Double(v2.getResultadoDeAplicarCondicion())));	
				 
				 return valoresAizq;
			}
			
		}
		
		else{
		/*en este caso tengo que filtrar aquellos que cumplan con la condicion con lo cual tengo que armar la condicion*/
			ScriptEngineManager manager = new ScriptEngineManager(); 
		    ScriptEngine interprete = manager.getEngineByName("js"); /*con esto armo la ecuacion*/
		    ArrayList<PuntajeEmpresa> empresasQueCumplenCond=new ArrayList<PuntajeEmpresa>();
		   /*y el lado derecho siempre van a hacer los mismos de la formula*/
		    String ladoder=String.valueOf(ladoDer);	
		    for(int i=0;i<valoresAizq.size();i++){
		    	/*aca hago esto para armar las formulas */
		    	String ladoizq= String.valueOf(valoresAizq.get(i).getResultadoDeAplicarCondicion());		    	
		    	/*armo la formula para filtrar*/
		    	String formula=ladoizq+comparador+ladoder;
		    	if((boolean) interprete.eval(formula)){
		    		/*con esto evaluo la expresion retorna true o false*/
		    		/*si cumple antonces agrego a la nueva lista*/
		    		empresasQueCumplenCond.add(valoresAizq.get(i));
		    	}
		    }
		    
		    
		    /*creo que falta ordenarlas se que estas empresas cumplen la condicion pero no se cuan bien*/
		    
		    return empresasQueCumplenCond;
		}		
		
	}

	public Condicion(LadoIzq ladoIzq, Double ladoDer, String comparador, int periodos) {
		super();
		this.ladoIzq = ladoIzq;
		this.ladoDer = ladoDer;
		this.comparador = comparador;
		this.periodos = periodos;
	};
	public Condicion(LadoIzq ladoIzq, int periodos, String criterio) {
		super();
		this.ladoIzq = ladoIzq;
		this.periodos = periodos;
		this.criterio = criterio;
	};

}