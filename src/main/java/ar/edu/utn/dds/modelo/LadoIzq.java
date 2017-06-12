package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;

public abstract class LadoIzq {
	
	/*a la hora de crear la metodologia siempre voy a saber que tipo de lado izquierdo estoy creando
	  y el indicador tambien*/
	
/*de alguna forma en la interfaz a la hora qeu ingresan el indicador deberia buscarlo con el traductor*/
	private Indicador indicador;
	private Traductor traductor;
	private ArrayList<Empresa> empresas=new ArrayList<Empresa>();
	
	

	public LadoIzq(Indicador indicador, Traductor traductor) {
		super();
		this.setIndicador(indicador);
		this.setTraductor(traductor);
	}
	public ArrayList<PuntajeEmpresa> calcularValor(int periodos)throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{

		
		//me quedo solo con las cuentas de empresa que pertenecen al periodo seria la misma estructura de la lista de emresas con menos cuentas
		
		ArrayList<Empresa> empresasAux= new ArrayList<Empresa>(traductor.getEmpresas());
       empresas=traductor.filtrarCuentasEnUnPeriodo(empresasAux,periodos);	
		
		ArrayList<PuntajeEmpresa> listaEmpresa= new ArrayList<PuntajeEmpresa> ();
		
		for (int i=0;i<empresas.size();i++){
			PuntajeEmpresa elementoLista=new PuntajeEmpresa();
			elementoLista.setNombreEmpresa(empresas.get(i).getNombre());
			listaEmpresa.add(elementoLista);
		}
		
		return listaEmpresa;
	}
	public ArrayList<PuntajeEmpresa> sumatoriaIndicadores(ArrayList<PuntajeEmpresa> listaEmpresas, int periodos) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador{
		ArrayList<Double> lista= new ArrayList<Double>();
	      lista=this.getTraductor().calcularAListaDeEmpresas(getEmpresas(), periodos, getIndicador());
	      
	      for (int i=0;(i<listaEmpresas.size()) && (i<lista.size());i++){
	    	  listaEmpresas.get(i).setResultadoDeAplicarCondicion(lista.get(i));
	      }
	      return listaEmpresas;
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}
	public Traductor getTraductor() {
		return traductor;
	}
	public void setTraductor(Traductor traductor) {
		this.traductor = traductor;
	}
	public Indicador getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
}
