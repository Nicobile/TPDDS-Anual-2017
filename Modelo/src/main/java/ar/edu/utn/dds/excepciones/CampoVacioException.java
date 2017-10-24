package ar.edu.utn.dds.excepciones;

public class CampoVacioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CampoVacioException(String msje_error) {
		super(msje_error);
	}
}
