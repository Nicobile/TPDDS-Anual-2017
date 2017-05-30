package ar.edu.utn.dds.excepciones;

public class NoSeEncuentraLaCuenta extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraLaCuenta(String msje_error) {
		super(msje_error);
	}
}
