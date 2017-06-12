package ar.edu.utn.dds.modelo;

import java.util.ArrayList;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuenta;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicador;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnEsaFecha;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresa;
import ar.edu.utn.dds.procesarArchivos.LineaArchivo;

public class Traductor {

	// creo lista empresas
	private ArrayList<Empresa> empresas = new ArrayList<Empresa>();

	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	private ExpressionParser parser = new ExpressionParser();

	/*------------------------------------------------------*/
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public ArrayList<String> listarIndicadodoresyCuentas(Empresa e, String fecha) {
		ArrayList<String> lista = new ArrayList<>();
		for (int i = 0; i < getIndicadores().size(); i++) {
			lista.add(getIndicadores().get(i).getNombre());
		}
		for (int i = 0; i < e.getCuentas().size(); i++) {
			lista.add(e.getCuentas().get(i).getNombre());

		}
		return lista;

	}

	public double calcular(String empresa, String fecha, String nombreIndicador) throws NoSeEncuentraLaEmpresa,
			NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha, NoSeEncuentraElIndicador {
		Indicador i = this.buscarIndicador(nombreIndicador);
		Operando operando = parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(this.obtenerEmpresa(empresa), fecha);
	}

	public ArrayList<Double> calcularAListaDeEmpresas(ArrayList<Empresa> empresas, int periodos, Indicador i)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador {
		ArrayList<Double> lista = new ArrayList<Double>();
		for (int j = 0; j < empresas.size(); j++) {
			
			double sumaDeIndicadorPeriodos = 0;
			for (int x = periodos; x > 0; x--) {
				int valor=2016-x;
				sumaDeIndicadorPeriodos = sumaDeIndicadorPeriodos+
				this.calcular(empresas.get(j).getNombre(),String.valueOf(valor), i.getNombre());
				
			}
		
			
			lista.add(sumaDeIndicadorPeriodos);
		}
		return lista;
	}

	public ArrayList<Empresa> compararAListaDeEmpresas(ArrayList<Empresa> empresas, int periodos, Indicador i, char criterio)
			throws NoSeEncuentraLaEmpresa, NoSeEncuentraLaCuenta, NoSeEncuentraLaCuentaEnEsaFecha,
			NoSeEncuentraElIndicador {
		
		for (int j = 0; j < empresas.size(); j++) {
			int valorInicial=2016-periodos;
			double periodoAnterior = (this.calcular(empresas.get(j).getNombre(), String.valueOf(valorInicial),i.getNombre()));
			
			for (int x = periodos; x > 0; x--) {
				int valor=2016-x;
				
				if (criterio == '>') {
					if (periodoAnterior <= this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),i.getNombre())){
						
					periodoAnterior = this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),i.getNombre());
				} else {
					empresas.remove(j);
					j=j-1;
					
				}			
		}
				if (criterio == '<') {
					if (periodoAnterior >= this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),i.getNombre())){						
					periodoAnterior = this.calcular(empresas.get(j).getNombre(), String.valueOf(valor),i.getNombre());
				} else {
					empresas.remove(j);
					j=j-1;
				}
			}
		
	}
			}return empresas;}

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
	public int buscarEmpresa(String nombreEmpresa) throws NoSeEncuentraLaEmpresa {
		try {
			for (int x = 0; x < empresas.size(); x++) {
				// pregunto si ya existe la empresa
				if (empresas.get(x).getNombre().equals(nombreEmpresa)) {
					return x;
				}

			}
		} catch (Exception e) {
			throw new NoSeEncuentraLaEmpresa("No se encontro la empresa especificada");
		}
		;
		return -1;
	}

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
		int i = this.buscarEmpresa(nombreEmpresa);
		return getEmpresas().get(i).obtenerValorDeCuenta(nombreCuenta, fecha);
	}

	public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo) throws NoSeEncuentraLaEmpresa {

		// recorro la lista que contiene todos los datos
		for (int x = 0; x < lineasArchivo.size(); x++) {

			int i;// indice de donde encuentra el elemento en la lista de
					// empresas ya existentes

			i = this.buscarEmpresa(lineasArchivo.get(x).getNombreEmpresa());
			// pregunto si ya existe la empresa

			if ((i >= 0)) {// si ya existe la empresa
				// creo una nueva cuenta
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), lineasArchivo.get(x).getFecha());

				this.getEmpresas().get(i).getCuentas().add(cuenta);

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
	public ArrayList<Empresa> filtrarCuentasEnUnPeriodo(ArrayList<Empresa>empresas,int periodos) {
		for (int i=0;i<empresas.size();i++){
			for(int x=periodos;x<0;x--)
			for(int j=0;j<empresas.get(i).getCuentas().size();j++){
				if(!(empresas.get(i).getCuentas().get(j).getFecha().equals(String.valueOf(2016-x)))){
					empresas.get(i).getCuentas().remove(j);
					j=j-1;
				}
				
			}}
		
			
		
	return empresas;
	
	}

	public ArrayList<Empresa> getEmpresas() {
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
