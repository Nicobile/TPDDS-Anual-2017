package ar.edu.utn.dds.excepciones;

public class EntradaDeDatosErroneaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntradaDeDatosErroneaException(String msje_error) {
		super(msje_error);
	}

}
