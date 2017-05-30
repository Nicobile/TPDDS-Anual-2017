package ar.edu.utn.dds.excepciones;

public class NoSeEncuentraLaCuentaEnEsaFecha extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraLaCuentaEnEsaFecha(String msje_error) {
		super(msje_error);
	}
}
