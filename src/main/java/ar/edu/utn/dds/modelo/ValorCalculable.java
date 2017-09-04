package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;

@Entity
@Table(name = "valoresCalculables")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)  
public abstract class ValorCalculable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="indicador_id")
	private Indicador indicador;
	
	private static List<Empresa> empresas;
	
	private static Traductor traductor;

	public ValorCalculable() {
		
	}
	
	public ValorCalculable(Indicador indicador, Traductor traductor) {
		super();
		this.setIndicador(indicador);
		this.setTraductor(traductor);
	}

	/*
	 * a la hora de crear la metodologia siempre voy a saber que tipo de lado
	 * izquierdo estoy creando y el indicador tambien
	 */

	public ValorCalculable(Traductor tradu) {
		super();
		traductor = tradu;
	}

	public ArrayList<PuntajeEmpresa> calcularValor(Periodo periodos, int anios)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		empresas = traductor.getEmpresas().stream().collect(Collectors.toList());

		ArrayList<PuntajeEmpresa> listaEmpresa = new ArrayList<PuntajeEmpresa>();

		empresas.stream().forEach(unaE -> listaEmpresa.add(new PuntajeEmpresa(unaE.getNombre())));

		return listaEmpresa;
	}

	public ArrayList<PuntajeEmpresa> sumatoriaIndicadores(ArrayList<PuntajeEmpresa> listaEmpresas, Periodo periodos)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<Double> lista = new ArrayList<Double>();
		lista = this.getTraductor().calcularAListaDeEmpresas(getEmpresas(), periodos, getIndicador());

		for (int i = 0; (i < listaEmpresas.size()) && (i < lista.size()); i++) {
			listaEmpresas.get(i).setResultadoDeAplicarCondicion(lista.get(i));
		}
		return listaEmpresas;
	}

	public ArrayList<PuntajeEmpresa> eliminarEmpresasQueNoCumplenCondicion(ArrayList<PuntajeEmpresa> listaEmpresas,
			List<Empresa> empresas) {

		listaEmpresas.removeIf(unE -> !empresas.stream().filter(unaE -> unaE.getNombre().equals(unE.getNombreEmpresa()))
				.findFirst().isPresent());

		return listaEmpresas;

	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> e) {
		empresas = e;
	}

	public Traductor getTraductor() {
		return traductor;
	}

	public void setTraductor(Traductor tradu) {
		traductor = tradu;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
}
