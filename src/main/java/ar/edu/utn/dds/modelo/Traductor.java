package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFechaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.procesarArchivos.LineaArchivo;

public class Traductor {

	// creo lista empresas
	private List<Empresa> empresas = new ArrayList<Empresa>();

	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	private ExpressionParser parser = new ExpressionParser();

	/*------------------------------------------------------*/
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public ArrayList<String> listarIndicadodoresyCuentas(Empresa e, String fecha) {
		ArrayList<String> lista = new ArrayList<>();
		
		getIndicadores().forEach(unI->lista.add(unI.getNombre()));
		
		e.getCuentas().forEach(unaC->lista.add(unaC.getNombre()));
		return lista;

	}

	public double calcular(String empresa, String fecha, String nombreIndicador) throws NoSeEncuentraLaEmpresaException,
			NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException, NoSeEncuentraElIndicadorException {
		Indicador i = this.buscarIndicador(nombreIndicador);
		Operando operando = parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(this.obtenerEmpresa(empresa), fecha);
	}
//HAY QUE MODIFICAR DEPENDIENDO DE COMO TRABAJEN LOS PERIODOS
	public ArrayList<Double> calcularAListaDeEmpresas(List<Empresa> empresas, int periodos, Indicador i)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
			NoSeEncuentraElIndicadorException {
		ArrayList<Double> lista = new ArrayList<Double>();
		for (int j = 0; j < empresas.size(); j++) {

			double sumaDeIndicadorPeriodos = 0;
			for (int x = periodos; x > 0; x--) {
				int valor = 2016 - x;
				sumaDeIndicadorPeriodos = sumaDeIndicadorPeriodos
						+ this.calcular(empresas.get(j).getNombre(), String.valueOf(valor), i.getNombre());

			}

			lista.add(sumaDeIndicadorPeriodos);
		}
		return lista;
	}

	public int eliminarEmpresa(List<Empresa> empresas, int j) {
		empresas.remove(j);

		if (j == 0) {
			j = -1;
		} else {
			j = j - 1;
		}
		return j;
	}
	// ESTOS DOS METODOS HAY UQE ESPERAR A VER COMO TRABAJAN LOS PERIODOS
	public List<Empresa> empresasConIndicadorCreciente(List<Empresa> empresas, int periodos, Indicador i) throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
			NoSeEncuentraElIndicadorException {

		for (int j = 0; j < empresas.size(); j++) {
			int valorInicial = 2016 - periodos;
			double periodoAnterior = (this.calcular(empresas.get(j).getNombre(), String.valueOf(valorInicial),i.getNombre()));

			for (int x = periodos; x > 0; x--) {
				int valor = 2016 - x;

				{
					if (periodoAnterior <= this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),
							i.getNombre())) {

						periodoAnterior = this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),
								i.getNombre());
					} else {
						j = eliminarEmpresa(empresas, j);
					}
					if (empresas.isEmpty()) {

						return empresas;

					}}
				}
		}
		return empresas;}

	public List<Empresa> empresasConIndicadorDecreciente(List<Empresa> empresas, int periodos, Indicador i) throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException,
	NoSeEncuentraElIndicadorException{

		for (int j = 0; j < empresas.size(); j++) {
			int valorInicial = 2016 - periodos;
			double periodoAnterior = (this.calcular(empresas.get(j).getNombre(), String.valueOf(valorInicial),
					i.getNombre()));

			for (int x = periodos; x > 0; x--) {
				int valor = 2016 - x;

					if (periodoAnterior >= this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),
							i.getNombre())) {
						periodoAnterior = this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),
								i.getNombre());
					} else {
						j = eliminarEmpresa(empresas, j);

					}
					if (empresas.isEmpty()) {
						return empresas;

					}
				

			}
		}
		return empresas;}
	

	

	public Indicador buscarIndicador(String ind) throws NoSeEncuentraElIndicadorException {
		try{
			return getIndicadores().stream().filter(unIndicador -> unIndicador.getNombre().equals(ind)).findFirst()
			.get();
			
		}

		catch(NoSuchElementException e){
		throw new NoSeEncuentraElIndicadorException("No se encontro en la lista de indicadores el indicador especificado");}

	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public void agregarIndicador(Indicador i) {

		getIndicadores().add(i);

	}

	/*----------------------------------------------------------------------------*/


	public Empresa obtenerEmpresa(String nombreEmpresa) throws NoSeEncuentraLaEmpresaException {
		try{
			return getEmpresas().stream().filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombreEmpresa)).findFirst().get();
		
		}
		catch( NoSuchElementException e){
			throw new NoSeEncuentraLaEmpresaException("No se encontro la empresa especificada");
		}
		
	

		
	}

	public double consultarValorCuenta(String nombreEmpresa, String nombreCuenta, String fecha)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnEsaFechaException {

		Empresa e= obtenerEmpresa(nombreEmpresa);
		return e.obtenerValorDeCuenta(nombreCuenta, fecha);
	}

	public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo) throws NoSeEncuentraLaEmpresaException {

		// recorro la lista que contiene todos los datos
		for (int x = 0; x < lineasArchivo.size(); x++) {
// si ya existe la empresa
			String nombreEmpresa=lineasArchivo.get(x).getNombreEmpresa();
			try{
				Empresa empresAux = getEmpresas().stream().filter(unaE-> unaE.getNombre().equals(nombreEmpresa)).findFirst().get();
			
				// creo una nueva cuenta
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),lineasArchivo.get(x).getValorCuenta(), lineasArchivo.get(x).getFecha());

				empresAux.agregarCuenta(cuenta);

			}catch(NoSuchElementException e){
				
				Empresa empresa = new Empresa(lineasArchivo.get(x).getNombreEmpresa());
				// creo la cuenta de la nueva empresa
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), lineasArchivo.get(x).getFecha());
				getEmpresas().add(empresa);// agrego la empresa a la lista
												// de
				// empresas
				empresa.agregarCuenta(cuenta);
				
			}
			
		}

	}
  

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}

	public ExpressionParser getParser() {
		return parser;
	}

	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}

}
