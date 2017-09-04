package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;
import javax.script.ScriptException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;

@Entity
@Table(name = "condiciones")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)  
public abstract class Condicion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="ladoIzq_id")
	private ValorCalculable ladoIzq;
	@ManyToOne
	@JoinColumn(name="periodo_id")
	private Periodo periodos;
	private int anios;
	@Transient
	public Boolean filtro;

	public Condicion() {

	}
	/*
	 * Aplica a todas las empresas el lado izq y los guarda en la lista SIN ORDENAR
	 */

	public List<PuntajeEmpresa> aplicar() throws ScriptException, NoSePudoOrdenarLaCondicionException,
			NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException,
			NoSeEncuentraElIndicadorException {

		List<PuntajeEmpresa> valoresAizq = new ArrayList<PuntajeEmpresa>();

		valoresAizq = ladoIzq.calcularValor(periodos, anios);

		return valoresAizq;
	}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public ValorCalculable getLadoIzq() {
		return ladoIzq;
	}

	public void setLadoIzq(ValorCalculable ladoIzq) {
		this.ladoIzq = ladoIzq;
	}

	public Periodo getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Periodo periodos) {
		this.periodos = periodos;
	}

	public Boolean getFiltro() {
		return filtro;
	}

	public void setFiltro(Boolean fil) {
		filtro = fil;
	}
}
