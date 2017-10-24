package ar.edu.utn.dds.excepciones;

public class NoSePudoOrdenarLaCondicionException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoSePudoOrdenarLaCondicionException(String msje_error) {
		super(msje_error);
	}
}
