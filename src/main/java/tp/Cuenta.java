package tp;

public class Cuenta {
	private String fecha;
	private String nombre;
	private int valor;
	public Cuenta(  String nombreCuenta,int valorCuenta, String fecha) {
        this.nombre= nombreCuenta;
        this.valor=valorCuenta;
        this.fecha=fecha;}


/*public void inicializate(LineaArchivo elementoColeccion){
	Cuenta cuenta= new Cuenta();
	cuenta.fecha=elementoColeccion.fecha;
	cuenta.valor=elementoColeccion.valorCuenta;
	cuenta.nombre=elementoColeccion.nombreCuenta;
}*/
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

public int getValor() {
	return valor;
}

public void setValor(int valor) {
	this.valor = valor;
}
}
