package ar.edu.utn.dds.excepciones;

public class NoSeEncuentraElIndicadorException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSeEncuentraElIndicadorException(String msje_error) {
		super(msje_error);
	}
}
