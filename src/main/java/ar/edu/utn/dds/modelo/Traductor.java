package ar.edu.utn.dds.modelo;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import antlr.ExpressionParser;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
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

	public ArrayList<String> listarIndicadoresyCuentas(Empresa e) {
		ArrayList<String> lista = new ArrayList<>();

		getIndicadores().forEach(unI -> lista.add(unI.getNombre()));

		e.getCuentas().forEach(unaC -> lista.add(unaC.getNombre()));
		return lista;

	}

	public double calcular(String empresa, Periodo periodo, String nombreIndicador)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		Indicador i = this.buscarIndicador(nombreIndicador);
		Operando operando = parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(this.obtenerEmpresa(empresa), periodo);
	}

	// HAY QUE MODIFICAR DEPENDIENDO DE COMO TRABAJEN LOS PERIODOS
	public ArrayList<Double> calcularAListaDeEmpresas(List<Empresa> empresas, Periodo periodo, Indicador i)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		ArrayList<Double> lista = new ArrayList<Double>();

		for (int j = 0; j < empresas.size(); j++) {

			double sumaDeIndicadorPeriodos = 0;

			// solo a las cuentas que pertenecen al periodo
			Iterator<Cuenta> listaCuentas = empresas.get(j).filtraCuentasEnPeriodo(periodo).iterator();
			while (listaCuentas.hasNext()) {
				// avanzo en la lista de cuentas que pertenecen al periodo
				// calculo el indicador a la empresa en el periodo
				// en realidad no uso las cuentas.... solo los periodos en los
				// que existen cuentes que estan ,comprendidos entre los
				// periodos que me piden desde la interfaz
				sumaDeIndicadorPeriodos += calcular(empresas.get(j).getNombre(), listaCuentas.next().getPeriodo(),
						i.getNombre());
			}

			lista.add(sumaDeIndicadorPeriodos);

		}

		return lista;
	}

	/*
	 * public int eliminarEmpresa(List<Empresa> empresas, int j) {
	 * empresas.remove(j);
	 * 
	 * if (j == 0) { j = -1; } else { j = j - 1; } return j; }
	 */
	public void eliminarEmpresa(List<Empresa> empresas, Empresa e) {
		if (empresas.contains(e))
			empresas.remove(e);
	}

	// ESTOS DOS METODOS HAY UQE ESPERAR A VER COMO TRABAJAN LOS PERIODOS
	/*
	 * public List<Empresa> empresasConIndicadorCreciente(List<Empresa>
	 * empresas, int anio, Indicador i) throws NoSeEncuentraLaEmpresaException,
	 * NoSeEncuentraLaCuentaException,
	 * NoSeEncuentraLaCuentaEnElPeriodoException,
	 * NoSeEncuentraElIndicadorException {
	 * 
	 * LocalDate diaDeHoy = LocalDate.now(); LocalDate diaInicio =
	 * diaDeHoy.minusYears(anio); Periodo periodo = new Periodo(diaInicio,
	 * diaDeHoy);
	 * 
	 * for (int j = 0; j < empresas.size(); j++) {
	 * 
	 * List<Cuenta> cuentas = empresas.get(j).filtraCuentasEnPeriodo(periodo);
	 * // de las cuentas me quedo con los periodos List<Periodo> periodos =
	 * cuentas.stream().map(unaC ->
	 * unaC.getPeriodo()).collect(Collectors.toList()); // elimino periodos
	 * iguales List<Periodo> listaPeriodos = new ArrayList<>(new
	 * HashSet<>(periodos)); Collections.sort(listaPeriodos, (p1, p2) ->
	 * p1.getFechaInicio().compareTo(p2.getFechaInicio()));
	 * 
	 * double periodoAnterior = (this.calcular(empresas.get(j).getNombre(),
	 * listaPeriodos.get(0), i.getNombre())); for (int x = 0; x <
	 * listaPeriodos.size(); x++) { try { if (periodoAnterior <=
	 * this.calcular(empresas.get(j).getNombre(), listaPeriodos.get(x),
	 * i.getNombre())) { periodoAnterior =
	 * this.calcular(empresas.get(j).getNombre(), listaPeriodos.get(x),
	 * i.getNombre()); } else { j = eliminarEmpresa(empresas, j);
	 * 
	 * } } catch (NoSeEncuentraLaCuentaEnElPeriodoException e) { } ; if
	 * (empresas.isEmpty()) { return empresas;
	 * 
	 * } } }
	 * 
	 * return empresas; }
	 */
	public List<Empresa> empresasConIndicadorCreciente(List<Empresa> empresas, int anio, Indicador i)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		LocalDate diaDeHoy = LocalDate.now();
		LocalDate diaInicio = diaDeHoy.minusYears(anio);
		Periodo periodo = new Periodo(diaInicio, diaDeHoy);
		List<Empresa> empresasConIndicadorCreciente = new ArrayList<>(empresas);

		empresas.stream().forEach(unaE -> {

			List<Cuenta> cuentas = unaE.filtraCuentasEnPeriodo(periodo);
			// de las cuentas me quedo con los periodos
			List<Periodo> periodos = cuentas.stream().map(unaC -> unaC.getPeriodo()).collect(Collectors.toList());
			// elimino periodos iguales
			List<Periodo> listaPeriodos = new ArrayList<>(new HashSet<>(periodos));
			// ordeno los periodos de menor a mayor, de esta manera se cual
			// periodo es anterior, y calculo su valor
			Collections.sort(listaPeriodos, (p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));

			List<Double> valorEnperiodos = new ArrayList<>();
			listaPeriodos.stream().forEach(unP -> {

				try {
					valorEnperiodos.add((this.calcular(unaE.getNombre(), unP, i.getNombre())));

				} catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {
				} catch (NoSeEncuentraLaEmpresaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSeEncuentraLaCuentaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSeEncuentraElIndicadorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
			// si no ordeno la lista es pq no esta ordenado en forma CRECIENTE

			empresasConIndicadorCreciente.removeIf(
					unaEmp -> (!valorEnperiodos.stream().sorted().collect(Collectors.toList()).equals(valorEnperiodos))
							&& unaEmp.equals(unaE));

		});
		return empresasConIndicadorCreciente;

	}

	public List<Empresa> empresasConIndicadorDecreciente(List<Empresa> empresas, int anio, Indicador i)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {

		LocalDate diaDeHoy = LocalDate.now();
		LocalDate diaInicio = diaDeHoy.minusYears(anio);
		Periodo periodo = new Periodo(diaInicio, diaDeHoy);
		List<Empresa> empresasConIndicadorDecreciente = new ArrayList<>(empresas);
		empresas.stream().forEach(unaE -> {

			List<Cuenta> cuentas = unaE.filtraCuentasEnPeriodo(periodo);
			// de las cuentas me quedo con los periodos
			List<Periodo> periodos = cuentas.stream().map(unaC -> unaC.getPeriodo()).collect(Collectors.toList());
			// elimino periodos iguales
			List<Periodo> listaPeriodos = new ArrayList<>(new HashSet<>(periodos));
			// ordeno los periodos de menor a mayor, de esta manera se cual
			// periodo es anterior, y calculo su valor
			Collections.sort(listaPeriodos, (p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));

			List<Double> valorEnperiodos = new ArrayList<>();
			listaPeriodos.stream().forEach(unP -> {

				try {
					valorEnperiodos.add((this.calcular(unaE.getNombre(), unP, i.getNombre())));

				} catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {
				} catch (NoSeEncuentraLaEmpresaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSeEncuentraLaCuentaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSeEncuentraElIndicadorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
			// si ordeno la lista es pq no esta ordenado en forma DECRECIENTE

			empresasConIndicadorDecreciente.removeIf(
					unaEmp -> (valorEnperiodos.stream().sorted().collect(Collectors.toList()).equals(valorEnperiodos))
							&& unaEmp.equals(unaE));

		});
		return empresasConIndicadorDecreciente;

	}

	public Indicador buscarIndicador(String ind) throws NoSeEncuentraElIndicadorException {
		try {
			return getIndicadores().stream().filter(unIndicador -> unIndicador.getNombre().equals(ind)).findFirst()
					.get();

		}

		catch (NoSuchElementException e) {
			throw new NoSeEncuentraElIndicadorException(
					"No se encontro en la lista de indicadores el indicador especificado");
		}

	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public void agregarIndicador(Indicador i) {

		getIndicadores().add(i);

	}

	/*----------------------------------------------------------------------------*/

	public Empresa obtenerEmpresa(String nombreEmpresa) throws NoSeEncuentraLaEmpresaException {
		try {
			return getEmpresas().stream().filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombreEmpresa)).findFirst()
					.get();

		} catch (NoSuchElementException e) {
			throw new NoSeEncuentraLaEmpresaException("No se encontro la empresa especificada");
		}

	}

	public double consultarValorCuenta(String nombreEmpresa, String nombreCuenta, Periodo periodo)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException {

		Empresa e = obtenerEmpresa(nombreEmpresa);
		return e.obtenerValorDeCuenta(nombreCuenta, periodo);
	}

	public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo) throws NoSeEncuentraLaEmpresaException {

		// recorro la lista que contiene todos los datos
		for (int x = 0; x < lineasArchivo.size(); x++) {

			// si ya existe la empresa
			String nombreEmpresa = lineasArchivo.get(x).getNombreEmpresa();
			// creo un nuevo periodo

			Periodo periodo = new Periodo(lineasArchivo.get(x).getFechaInicio(), lineasArchivo.get(x).getFechaFin());

			try {
				Empresa empresAux = getEmpresas().stream().filter(unaE -> unaE.getNombre().equals(nombreEmpresa))
						.findFirst().get();

				// creo una nueva cuenta
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), periodo);

				empresAux.agregarCuenta(cuenta);

			} catch (NoSuchElementException e) {

				Empresa empresa = new Empresa(lineasArchivo.get(x).getNombreEmpresa());
				// creo la cuenta de la nueva empresa
				Cuenta cuenta = new Cuenta(lineasArchivo.get(x).getNombreCuenta(),
						lineasArchivo.get(x).getValorCuenta(), periodo);
				empresa.agregarCuenta(cuenta);
				getEmpresas().add(empresa);// agrego la empresa a la lista
											// de
				// empresas

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
