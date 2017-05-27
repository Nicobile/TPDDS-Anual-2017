package ar.edu.utn.dds.procesarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Operacion;

import ar.edu.utn.dds.modelo.Traductor;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {
	private Traductor t = new Traductor();

	public ProcesarIndicadores(Traductor t) {

		super();
		this.t = t;
	}

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
					t.agregarIndicador(indicador);

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
		t.agregarIndicador(indicador);
		return indicador;
	}

	ArrayList<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e) {

		return e.getCuentas();
	}

}