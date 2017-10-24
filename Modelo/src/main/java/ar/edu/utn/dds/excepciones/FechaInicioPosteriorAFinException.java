package ar.edu.utn.dds.excepciones;

public class FechaInicioPosteriorAFinException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FechaInicioPosteriorAFinException(String msje_error) {
		super(msje_error);
	}
}
