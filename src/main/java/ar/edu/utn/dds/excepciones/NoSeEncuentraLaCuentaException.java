package ar.edu.utn.dds.excepciones;

public class NoSeEncuentraLaCuentaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraLaCuentaException(String msje_error) {
		super(msje_error);
	}
}
