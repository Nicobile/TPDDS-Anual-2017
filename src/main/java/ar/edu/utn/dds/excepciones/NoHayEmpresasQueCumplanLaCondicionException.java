package ar.edu.utn.dds.excepciones;

public class NoHayEmpresasQueCumplanLaCondicionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoHayEmpresasQueCumplanLaCondicionException(String msje_error) {
		super(msje_error);
	}
}
