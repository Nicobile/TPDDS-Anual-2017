package ar.edu.utn.dds.excepciones;

public class ErrorFechaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ErrorFechaException(String msje_error) {
		super(msje_error);
	}

}
