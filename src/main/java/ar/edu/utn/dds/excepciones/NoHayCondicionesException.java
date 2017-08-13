package ar.edu.utn.dds.excepciones;

public class NoHayCondicionesException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoHayCondicionesException(String msje_error) {
		super(msje_error);
	}
}
