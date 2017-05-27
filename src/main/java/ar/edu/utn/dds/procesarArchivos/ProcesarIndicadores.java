package ar.edu.utn.dds.procesarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import antlr.ExpressionParser;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Operacion;
import ar.edu.utn.dds.modelo.Operando;
import excepciones.NoSeEncuentraEnLaLista;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {

	private ExpressionParser parser = new ExpressionParser();// para buscar
																// sobre la cual
																// estoy
	public ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	void leerExcel(String archivo) throws IOException, FileNotFoundException {

		File inputWorkbook = new File(archivo);
		Workbook w = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);

			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);// nombre indicador
				Cell cell2 = sheet.getCell(1, i);// operacion

				CellType type = cell.getType();

				if (type == CellType.LABEL) {

					Operacion operacion = new Operacion();
					operacion.setOperacion(cell2.getContents());

					Indicador indicador = new Indicador(cell.getContents(), operacion);

					// seteo el nombre del indicador
					indicadores.add(indicador);

				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

	}

	public Indicador cargarIndPredefinidos(String nombre, String op) {

		Operacion operacion = new Operacion();
		operacion.setOperacion(op);
		Indicador indicador = new Indicador(nombre, operacion);
		indicadores.add(indicador);
		return indicador;
	}

	ArrayList<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e) {

		return e.getCuentas();
	}

	public ArrayList<String> listarIndicadodoresyCuentas(Empresa e, String fecha) {
		ArrayList<String> lista = new ArrayList<>();
		for (int i = 0; i < indicadores.size(); i++) {
			lista.add(indicadores.get(i).getNombre());
		}
		for (int i = 0; i < e.getCuentas().size(); i++) {
			lista.add(e.getCuentas().get(i).getNombre());

		}
		return lista;

	}

	public double calcular(Empresa e, String fecha, String nombreIndicador) throws NoSeEncuentraEnLaLista {
		Indicador i = this.buscarIndicador(nombreIndicador);
		Operando operando = parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(e, fecha);
	}

	public Indicador buscarIndicador(String ind) throws NoSeEncuentraEnLaLista {// busco
																				// un
																				// indicador
																				// de
																				// la
		// lista ya sea desde pq es
		// predefinido o pq es de
		// una lista

		if (this.getIndicadores().stream().filter(unInd -> unInd.getNombre().equals(ind)).findFirst().isPresent()) {

			return this.getIndicadores().stream().filter(unIndicador -> unIndicador.getNombre().equals(ind)).findFirst()
					.get();
		}
		throw new NoSeEncuentraEnLaLista("No se encontro en la lista el indicador especificado");

	}

	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

}