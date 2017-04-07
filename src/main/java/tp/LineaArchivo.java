package tp;



	public class LineaArchivo {
	    
	    String nombreEmpresa;
	    String nombreCuenta;
	    int valorCuenta;
	    
	    public LineaArchivo( String nombreEmpresa, String nombreCuenta,int valorCuenta) {
	        this.nombreCuenta = nombreCuenta;
	        this.nombreEmpresa=nombreEmpresa;
	        this.valorCuenta=valorCuenta;	        
	        
	    }

		public String getNombreEmpresa() {
			return nombreEmpresa;
		}

		public void setNombreEmpresa(String nombreEmpresa) {
			this.nombreEmpresa = nombreEmpresa;
		}

		public String getNombreCuenta() {
			return nombreCuenta;
		}

		public void setNombreCuenta(String nombreCuenta) {
			this.nombreCuenta = nombreCuenta;
		}

		public int getValorCuenta() {
			return valorCuenta;
		}

		public void setValorCuenta(int valorCuenta) {
			this.valorCuenta = valorCuenta;
		}

}
