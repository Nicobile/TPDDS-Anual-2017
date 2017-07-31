package ar.edu.utn.dds.procesarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;

import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;

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

	public void leerExcel(String archivo) throws IOException, FileNotFoundException {

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

					Indicador indicador = new Indicador(cell.getContents(), cell2.getContents());

					// seteo el nombre del indicador
					t.agregarIndicador(indicador);

				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

	}

	public Indicador cargarIndPredefinidos(String nombre, String op) {

		Indicador indicador = new Indicador(nombre, op);
		t.agregarIndicador(indicador);
		return indicador;
	}

	List<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e) {

		return e.getCuentas();
	}

}