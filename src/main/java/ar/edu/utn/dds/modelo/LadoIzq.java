package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

public abstract class LadoIzq {
	
	/*a la hora de crear la metodologia siempre voy a saber que tipo de lado izquierdo estoy creando
	  y el indicador tambien*/
	
/*de alguna forma en la interfaz a la hora qeu ingresan el indicador deberia buscarlo con el traductor*/
	private Indicador indicador;

	public LadoIzq(Indicador indicador) {
		super();
		this.indicador = indicador;
	}
	public ArrayList<PuntajeEmpresa> calcularValor(ArrayList<Empresa> empresas,int periodos){
		
		ArrayList<PuntajeEmpresa> listaEmpresa= new ArrayList<PuntajeEmpresa> ();
		PuntajeEmpresa elementoLista=new PuntajeEmpresa();
		for (int i=0;i<empresas.size();i++){
			elementoLista.setNombreEmpresa(empresas.get(i).getNombre());
			listaEmpresa.add(elementoLista);
		}
		
		return listaEmpresa;
	}
}
