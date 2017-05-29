package excepciones;

public class NoSeEncuentraCuenta extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraCuenta(String msje_error) {
		super(msje_error);
	}
}
