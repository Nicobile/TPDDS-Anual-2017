package ar.edu.utn.dds.modeloWeb;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import ar.edu.utn.dds.modelo.Periodo;

public class Resultado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -175077693679840764L;
	private static final AtomicInteger count = new AtomicInteger(0);
	private String id;
	private String resultado;
	private String indicador;
	private String nombreEmpresa;
	private Periodo periodo;

	public Resultado(String indicador, String nombreEmpresa, Periodo periodo) {
		super();
		this.indicador = indicador;
		this.nombreEmpresa = nombreEmpresa;
		this.periodo = periodo;
	}

	public Resultado(String resultado, String indicador, String nombreEmpresa, Periodo periodo) {
		super();

		this.id = String.valueOf(count.getAndIncrement());
		this.resultado = resultado;
		this.indicador = indicador;
		this.nombreEmpresa = nombreEmpresa;
		this.periodo = periodo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicador == null) ? 0 : indicador.hashCode());
		result = prime * result + ((nombreEmpresa == null) ? 0 : nombreEmpresa.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resultado other = (Resultado) obj;
		if (indicador == null) {
			if (other.indicador != null)
				return false;
		} else if (!indicador.equals(other.indicador))
			return false;
		if (nombreEmpresa == null) {
			if (other.nombreEmpresa != null)
				return false;
		} else if (!nombreEmpresa.equals(other.nombreEmpresa))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}
}
