package ar.edu.utn.dds.modelo;

import java.util.ArrayList;
import java.util.List;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
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

	public double calcular(String empresa, String fecha, String nombreIndicador) throws NoSeEncuentraLaEmpresa,
			NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		Indicador i = this.buscarIndicador(nombreIndicador);
		Operando operando = parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(this.obtenerEmpresa(empresa), fecha);
	}

	public ArrayList<Double> calcularAListaDeEmpresas(List<Empresa> empresas, int periodos, Indicador i)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador {
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
	public List<Empresa> empresasConIndicadorCreciente(List<Empresa> empresas, int periodos, Indicador i) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador {

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

	public List<Empresa> empresasConIndicadorDecreciente(List<Empresa> empresas, int periodos, Indicador i) throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
	NoSeEncuentraElIndicador{

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
	

	

	public Indicador buscarIndicador(String ind) throws NoSeEncuentraElIndicador {

		if (this.getIndicadores().stream().filter(unInd -> unInd.getNombre().equals(ind)).findFirst().isPresent()) {

			return this.getIndicadores().stream().filter(unIndicador -> unIndicador.getNombre().equals(ind)).findFirst()
					.get();
		}
		throw new NoSeEncuentraElIndicador("No se encontro en la lista de indicadores el indicador especificado");

	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public void agregarIndicador(Indicador i) {

		getIndicadores().add(i);

	}

	/*----------------------------------------------------------------------------*/


	public Empresa obtenerEmpresa(String nombreEmpresa) throws NoSeEncuentraLaEmpresa {
		if (this.getEmpresas().stream().filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombreEmpresa)).findFirst()
				.isPresent()) {

			return this.getEmpresas().stream().filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombreEmpresa))
					.findFirst().get();

		}
		throw new NoSeEncuentraLaEmpresa("No se encontro la empresa especificada");
	}

	public double consultarValorCuenta(String nombreEmpresa, String nombreCuenta, String fecha)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha {

		Empresa e= this.obtenerEmpresa(nombreEmpresa);
		return getEmpresas().stream().filter(unaE-> unaE.getNombre().equals(e.getNombre())).findFirst().get().obtenerValorDeCuenta(nombreCuenta, fecha);
	}

	public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo) throws NoSeEncuentraLaEmpresa {

		// recorro la lista que contiene todos los datos
		for (int x = 0; x < lineasArchivo.size(); x++) {
// si ya existe la empresa
			String nombreEmpresa=lineasArchivo.get(x).getNombreEmpresa();
			if(getEmpresas().stream().filter(unaE-> unaE.getNombre().equals(nombreEmpresa)).findFirst().isPresent()){
				// creo una nueva cuenta
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), lineasArchivo.get(x).getFecha());

				getEmpresas().stream().filter(unaE-> unaE.getNombre().equals(nombreEmpresa)).findFirst().get().getCuentas().add(cuenta);

			}
			// la empresa no existia entonces la creo
			else {

				ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
				Empresa empresa = new Empresa(lineasArchivo.get(x).getNombreEmpresa(), cuentas);
				// creo la cuenta de la nueva empresa
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), lineasArchivo.get(x).getFecha());
				this.getEmpresas().add(empresa);// agrego la empresa a la lista
												// de
				// empresas
				empresa.getCuentas().add(cuenta);
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
