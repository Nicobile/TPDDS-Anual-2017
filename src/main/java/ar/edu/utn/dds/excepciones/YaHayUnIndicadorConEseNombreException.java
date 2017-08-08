package ar.edu.utn.dds.excepciones;

public class YaHayUnIndicadorConEseNombreException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public YaHayUnIndicadorConEseNombreException(String msje_error) {
		super(msje_error);
	}
}
