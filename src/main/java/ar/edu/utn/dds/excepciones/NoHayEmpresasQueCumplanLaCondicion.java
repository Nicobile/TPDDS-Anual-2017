package ar.edu.utn.dds.excepciones;

public class NoHayEmpresasQueCumplanLaCondicion extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoHayEmpresasQueCumplanLaCondicion(String msje_error) {
		super(msje_error);
	}
}
