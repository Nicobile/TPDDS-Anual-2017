package ar.edu.utn.dds.modelo;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import ar.edu.utn.dds.antlr.ExpressionParser;
import ar.edu.utn.dds.entidades.Cuentas;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.entidades.Periodos;
import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.persistencia.Utilidades;
import ar.edu.utn.dds.procesarArchivos.LineaArchivo;

public class Traductor {

	private List<Empresa> empresas = new ArrayList<Empresa>();
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	private List<Metodologia> metodologias = new ArrayList<Metodologia>();
	private ExpressionParser parser = new ExpressionParser();
	private static Logger log = Logger.getLogger(Principal.class);

	public void cargarTraductor() {
		empresas = Empresas.getEmpresas();
		indicadores = Indicadores.getIndicadores();

	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void agregarMetodologia(Metodologia metodologia) {

		if (this.getMetodologias().contains(metodologia)) {
			throw new MetodologiaYaExisteException("Ya existe la metodologia que se quiere agregar");
		}
		metodologias.add(metodologia);

	}

	public Metodologia buscarMetodologia(String nombre) {
		return this.getMetodologias().stream().filter(unaMetodologia -> unaMetodologia.getNombre().equals(nombre))
				.findFirst().get();
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

	/*
	 * por cada periodo perteneciente al PERIODO INGRESADO POR INTERFAZ, por cada
	 * empresa, calculo el indicador en cada uno de esos periodos y los sumo
	 */
	public ArrayList<Double> calcularAListaDeEmpresas(List<Empresa> empresas, Periodo periodo, Indicador i) {

		ArrayList<Double> lista = new ArrayList<Double>();

		empresas.stream().forEach(unaE -> {
			ArrayList<Double> listaAux = new ArrayList<Double>();
			/* solo a las cuentas que pertenecen al periodo */
			List<Cuenta> cuentas = unaE.filtraCuentasEnPeriodo(periodo);

			/* de las cuentas me quedo con los periodos */
			List<Periodo> periodos = cuentas.stream().map(unaC -> unaC.getPeriodo()).collect(Collectors.toList());
			/* elimino periodos iguales */
			List<Periodo> listaPeriodos = new ArrayList<>(new HashSet<>(periodos));
			/* periodo es anterior, y calculo su valor */
			Collections.sort(listaPeriodos, (p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));

			listaPeriodos.stream().forEach(unP -> {

				/*
				 * avanzo en la lista de cuentas que pertenecen al periodo calculo el indicador
				 * a la empresa en el periodo en realidad no uso las cuentas.... solo los
				 * periodos en los que existen cuentes que estan ,comprendidos entre los
				 * periodos que me piden desde la interfaz
				 */

				if (unaE.tieneCuentasEnUnPeriodo(unP)) {
					try {
						listaAux.add(calcular(unaE.getNombre(), unP, i.getNombre()));
					} catch (NoSeEncuentraLaEmpresaException e) {
						log.fatal("No se encontro la empresa");
					} catch (NoSeEncuentraLaCuentaException e) {
						log.fatal("No se encontro la cuenta");
					} catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {
						log.fatal("No se encontro la cuenta en el periodo");
					} catch (NoSeEncuentraElIndicadorException e) {
						log.fatal("No se encontro el indicador");
					}
				}

			});

			double sum = listaAux.stream().mapToDouble(unV -> unV).sum();

			lista.add(sum);
		});
		return lista;
	}

	public void agregarIndicador(Indicador i) {
		getIndicadores().add(i);
	}

	public Empresa obtenerEmpresa(String nombreEmpresa) throws NoSeEncuentraLaEmpresaException {
		try {
			return getEmpresas().stream().filter(unaEmpresa -> unaEmpresa.getNombre().equals(nombreEmpresa)).findFirst()
					.get();
		} catch (NoSuchElementException e) {
			throw new NoSeEncuentraLaEmpresaException("No se encontro la empresa especificada");
		}
	}

	public Indicador buscarIndicador(String ind) throws NoSeEncuentraElIndicadorException {
		try {
			return getIndicadores().stream().filter(unIndicador -> unIndicador.getNombre().equals(ind)).findFirst()
					.get();
		} catch (NoSuchElementException e) {
			throw new NoSeEncuentraElIndicadorException(
					"No se encontro en la lista de indicadores el indicador especificado");
		}
	}

	public void armarListaEmpresas(ArrayList<LineaArchivo> lineasArchivo) throws NoSeEncuentraLaEmpresaException {

		HashSet<Periodo> periodos = new HashSet<>();

		/* recorro la lista que contiene todos los datos */

		lineasArchivo.stream().forEach(unaLinea -> {

			/* si ya existe la empresa */
			String nombreEmpresa = unaLinea.getNombreEmpresa();
			/* creo un nuevo periodo */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			LocalDate fechaI = LocalDate.parse(unaLinea.getFechaInicio(), formatter);
			LocalDate fechaF = LocalDate.parse(unaLinea.getFechaFin(), formatter);
			Periodo periodo = new Periodo(fechaI, fechaF);
			periodos.add(periodo);// esta lista contiene periodos sin repetidos

			try {
				Empresa empresAux = getEmpresas().stream().filter(unaE -> unaE.getNombre().equals(nombreEmpresa))
						.findFirst().get();

				// creo una nueva cuenta
				Cuenta cuenta = new Cuenta(unaLinea.getNombreCuenta(), unaLinea.getValorCuenta(), periodo);
				empresAux.agregarCuenta(cuenta);

			} catch (NoSuchElementException e) {

				Empresa empresa = new Empresa(unaLinea.getNombreEmpresa(), unaLinea.getFechaInscripcion());
				/* creo la cuenta de la nueva empresa */
				Cuenta cuenta = new Cuenta(unaLinea.getNombreCuenta(), unaLinea.getValorCuenta(), periodo);

				empresa.agregarCuenta(cuenta);
				/* agrego la empresa a la lista de empresas */
				getEmpresas().add(empresa);

			}
		});
		// me traigo los objetos almacenados en la base de datos
		List<Cuenta> cuentas = Cuentas.setCuentas();
		List<Empresa> empresas = Empresas.setEmpresas();
		List<Periodo> periodosDB = Periodos.setPeriodos();
		// hay que ver como verificar que las cuentas y periodos sean lo mismo
		EntityManager entityManager = Utilidades.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			periodos.stream().forEach(unP -> {
				if (!(periodosDB.contains(unP))) {
					Periodos.persistirPeriodoConEm(unP, entityManager);
				}
				List<Cuenta> cuentasPersistidas = new ArrayList<>();
				getEmpresas().stream().forEach(unaE -> {
					unaE.getCuentas().stream().forEach(unaC -> {
						cuentasPersistidas.clear();
						if (unaC.getPeriodo().equals(unP)) {
							if (!(cuentas.contains(unaC))) {
								unaC.setPeriodo(unP);
								Cuentas.persistirCuentaConEm(unaC, entityManager);
								cuentasPersistidas.add(unaC);

							}

						}

					});

					if (!(empresas.contains(unaE))) {

						Empresas.persistirEmpresaConEm(unaE, entityManager);

					}

				});
				Cuentas.asignarCuentas(cuentasPersistidas);

			});
			transaction.commit();

		} catch (PersistenceException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new PersistenceException("No se pudo persistir el objeto" + e.getMessage());
		} finally {
			if (entityManager != null) {
				Utilidades.closeEntityManager();
			}

		}
	}

	public void eliminarEmpresa(List<Empresa> empresas, Empresa e) {
		if (empresas.contains(e))
			empresas.remove(e);
	}

	public PuntajeEmpresa buscarEmpresaEnPuntajeEmpresa(List<PuntajeEmpresa> lista, String pe) {
		return lista.stream().filter(unaE -> unaE.getNombreEmpresa().equals(pe)).findFirst().get();
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
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
