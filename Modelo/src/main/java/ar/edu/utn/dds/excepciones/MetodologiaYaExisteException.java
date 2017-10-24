package ar.edu.utn.dds.excepciones;

public class MetodologiaYaExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MetodologiaYaExisteException(String msje_error) {
		super(msje_error);
	}
}
