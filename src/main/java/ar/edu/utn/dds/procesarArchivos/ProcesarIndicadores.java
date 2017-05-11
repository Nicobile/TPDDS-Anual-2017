package ar.edu.utn.dds.procesarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.parser.ExpressionParser;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {
	private LectorArchivo lector;// me permite conocer a la lista de empresas
									// para buscar sobre la cual estoy
									// calculando

	public ProcesarIndicadores(LectorArchivo lector) {
		super();
		this.lector = lector;
	}

	SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	public ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	// leo el excel y lo cargo en la lista de indicadores con el nombre del
	// indicador y la operacion ver si al excel agregar la empresa
	// formato de excel columna 1 nombre indicador columna 2 operacion columna 3
	// nombre de la empresa
	void leerExcel(String archivo) throws IOException, FileNotFoundException {

		File inputWorkbook = new File(archivo);
		Workbook w = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			// for (int j = 0; j < sheet.getColumns(); j++) {

			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				Cell cell2 = sheet.getCell(1, i);
				Cell cell3 = sheet.getCell(2, i);
				CellType type = cell.getType();

				if (type == CellType.LABEL) {
					
					
					

					Indicador indicador = new Indicador(cell.getContents(),cell2.getContents(),cell3.getContents());
					// seteo el nombre del indicador
					indicadores.add(indicador);
					/*
					 * System.out.println("I got a label " +
					 * cell.getContents()+cell2.getContents()+cell3.getContents(
					 * ));
					 */

				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

	}

	public Indicador cargarIndPredefinidos(String nombre, String operacion, String nombreDeEmpresa) {

		Indicador indicador = new Indicador(nombre, operacion, nombreDeEmpresa);
		indicadores.add(indicador);
		return indicador;
	}

	public Empresa buscarEmpresaSobreLaQueIndicadorSeCalcula(Indicador ind) {
		// busco en la lista de empresas el nombre de la empresadel indicador

		for (int i = 0; i < lector.getEmpresas().size(); i++) {

			if (lector.getEmpresas().get(i).getNombre().equals(ind.getNombreEmpresa())) {
				return lector.getEmpresas().get(i);
			}
		}
		return null;
	}

	public ArrayList<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e) {

		return e.getCuentas();
	}

	public ArrayList<Cuenta> obtenerSoloLasCuentasMasRecientesDeEmpresa(Empresa e) throws ParseException {// carga
																											// a
																											// cuentasEmpresa
		ArrayList<Cuenta> c = this.buscarDeUnaEmpresaTodasSusCuentas(e);
		ArrayList<Cuenta> cuentasEmpresa = new ArrayList<Cuenta>();
		for (int i = 0; i < c.size(); i++) {
			int x = existeLaCuenta(c.get(i), cuentasEmpresa);// existe la cuenta
																// devuelve el
																// indice donde
																// se la
																// encontro
			if (x < 0) {// no existe la cuenta en la lista
				cuentasEmpresa.add(c.get(i));
			} else {
				Date ultimaFecha = formatoFecha.parse(c.get(i)
						.getFecha());/*
										 * de la cuenta de la lista no filtrada
										 * por ultimas cuentas de empresa //
										 * obtengo // la // fecha
										 */

				if ((ultimaFecha.compareTo(formatoFecha.parse(cuentasEmpresa.get(x)
						.getFecha()))) > 0) {/*
												 * comparo la fecha de la cuenta
												 * con la que encontre en la
												 * lista de las ultimas cuentas
												 * de la empresa
												 */
					// ultimafecha seria la fecha mas reciente,es decir seria la
					// que esta en la lista no filtrada

					cuentasEmpresa.set(x, c.get(i));// reemplazo ne la misma
													// posicion la cuenta pq la
													// que encontre erra mas
													// reciente que la que
													// estaba
				}
			}
		}
		return cuentasEmpresa;
	}

	private int existeLaCuenta(Cuenta c, ArrayList<Cuenta> cuentasEmpresa) {
		for (int i = 0; i < cuentasEmpresa.size(); i++) {
			if (cuentasEmpresa.get(i).getNombre().equals(c.getNombre())) {
				return i;// retorno la posicion donde encuentro la empresa
			}
		}
		return -1;

	}
	public ArrayList<String> descomponerString(String operaciones,
			ArrayList<Cuenta> cuentas) {/*
										 * el // nombre // de // indicador // ni
										 * // cuenta // deben // tener //
										 * numeros
										 */
		ArrayList<String> operacionDeIndicadorDescompuesta = new ArrayList<String>();
		char[] c = operaciones.toCharArray();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < c.length; i++) {

			if (c[i] == '+' || c[i] == '-' || c[i] == '(' || c[i] == ')' || c[i] == '*' || c[i] == '/') {
				operacionDeIndicadorDescompuesta.add(Character.toString(c[i]));
			} else {
				s.append(c[i]);
			}
			Cuenta cue = this.buscarCuenta(s.toString(), cuentas);
			if (cue != null) {
				operacionDeIndicadorDescompuesta.add(String.valueOf(cue.getValor()));
				s.setLength(0);
			}
			Indicador ind = this.buscarIndicador(s.toString());
			if (ind != null) {
				operacionDeIndicadorDescompuesta.add(ind.getOperacion());
				
				s.setLength(0);
			}
			if (c[i] == '0' || c[i] == '1' || c[i] == '2' || c[i] == '3' || c[i] == '4' || c[i] == '5' || c[i] == '6'
					|| c[i] == '7' || c[i] == '8' || c[i] == '9') {
				operacionDeIndicadorDescompuesta.add(Character.toString(c[i]));
			}
		}

		return operacionDeIndicadorDescompuesta;

	}

	private Indicador buscarIndicador(String ind) {// busco un indicador de la
													// lista ya sea desde pq es
													// predefinido o pq es de
													// una lista
		for (int i = 0; i < indicadores.size(); i++) {
			if (indicadores.get(i).getNombre().equals(ind)) {
				return indicadores.get(i);
			}
		}
		return null;
	}

	private Cuenta buscarCuenta(String nombreCuenta, ArrayList<Cuenta> cuentas) {

		for (int i = 0; i < cuentas.size(); i++) {

			if (cuentas.get(i).getNombre().equals(nombreCuenta)) {
				return cuentas.get(i);
			}
		}
		return null;
	}


	public int calcularIndicador(Indicador i) throws ParseException {
		ArrayList<Cuenta> cuentasMasRecientesEmpresa = new ArrayList<Cuenta>();
		ArrayList<String> listaOperacionDelIndicador = new ArrayList<String>();
		cuentasMasRecientesEmpresa = this
				.obtenerSoloLasCuentasMasRecientesDeEmpresa(this.buscarEmpresaSobreLaQueIndicadorSeCalcula(
						i));/*
							 * a // cuentasEmpresa // le // agrego // solo //
							 * las // cuentas // de // la // empresa // sobre //
							 * la // que // calculo // el // indicador
							 */
		listaOperacionDelIndicador = this.descomponerString(i.getOperacion(), cuentasMasRecientesEmpresa);
		return i.operar(this.armarExpresion(listaOperacionDelIndicador));
	}


	

	private String armarExpresion(
			ArrayList<String> listaDeOperacion) {/*
													 * los // signos // -deben
													 * // ir // acompañados //
													 * por // un // parentesis
													 * // luego
													 */
		String s = new String();
		for (int i = 0; i < listaDeOperacion.size(); i++) {

			s = s.concat(listaDeOperacion.get(i));
		}
		return s;
	}

	// Getters and setters
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public static void main(String[] args) throws IOException, ParseException {
		LectorArchivo lector = new LectorArchivo();
		// lector.leerArchivo();
		ProcesarIndicadores p = new ProcesarIndicadores(lector);
		// p.leerExcel();
		ExpressionParser e = new ExpressionParser();
		ArrayList<String> lista = new ArrayList<>();
		ArrayList<Cuenta> cuentas = new ArrayList<>();
		Cuenta cuentaA = new Cuenta("cuentaA", 1, "12/10/1976");
		Cuenta cuentaB = new Cuenta("cuentaB", 122, "12/10/1686");
		Cuenta cuentaC = new Cuenta("cuentaC", 182, "12/10/1696");
		Cuenta cuentaD = new Cuenta("cuentaD", 13, "12/10/1676");
		Cuenta cuentaE = new Cuenta("cuentaA", 10, "12/10/2016");
		Cuenta cuentaF = new Cuenta("cuentaA", 1, "12/10/1986");
		cuentas.add(cuentaA);
		cuentas.add(cuentaB);
		cuentas.add(cuentaC);
		cuentas.add(cuentaD);
		cuentas.add(cuentaE);
		cuentas.add(cuentaF);
	/*	Indicador i = new Indicador("IndicadorA", "(cuentaB-(cuentaC)*cuentaA)*cuentaD", "Facebook");
		Indicador y = new Indicador("IndicadorB", "(cuentaB-(IndicadorA)*cuentaA)*cuentaD", "Facebook");
		Indicador j = new Indicador("IndicadorC", "(cuentaB-(cuentaC)*cuentaA)*cuentaD", "Facebook");
		Indicador k = new Indicador("IndicadorD", "(cuentaB-(cuentaC)*IndicadorA)*cuentaD", "Facebook");
		Indicador h = new Indicador("IndicadorE", "(IndicadorD)*IndicadorA-(IndicadorB)", "Facebook");
		Indicador r = p.cargarIndPredefinidos("IndicadorR", "(cuentaB-(cuentaC)*cuentaA)*cuentaD", "RIco");
		p.indicadores.add(i);
		p.indicadores.add(y);
		p.indicadores.add(j);
		p.indicadores.add(k);
		p.indicadores.add(h);

		lista = p.descomponerString(k.getOperacion(), cuentas);
		/*
		 * for(int x=0;x<lista.size();x++){ System.out.print(lista.get(x));
		 * //System.out.print(p.descomponerString(p.armarExpresion(lista),
		 * cuentas)); }
		 */
		/*System.out.print(r.operar(p.armarExpresion(lista)));*/

	}

}