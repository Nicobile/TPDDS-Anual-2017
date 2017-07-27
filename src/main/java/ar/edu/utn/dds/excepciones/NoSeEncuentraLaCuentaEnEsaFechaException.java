package ar.edu.utn.dds.excepciones;

public class NoSeEncuentraLaCuentaEnEsaFechaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraLaCuentaEnEsaFechaException(String msje_error) {
		super(msje_error);
	}
}
