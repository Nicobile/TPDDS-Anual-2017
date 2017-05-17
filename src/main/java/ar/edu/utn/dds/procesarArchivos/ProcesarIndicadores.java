package ar.edu.utn.dds.procesarArchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import antlr.ExpressionParser;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Operacion;
import ar.edu.utn.dds.modelo.Operando;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {
	
	private ExpressionParser parser = new ExpressionParser();// para buscar sobre la cual estoy
	public ArrayList<Indicador> indicadores = new ArrayList<Indicador>();




	void leerExcel(String archivo) throws IOException, FileNotFoundException {

		File inputWorkbook = new File(archivo);
		Workbook w = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			// for (int j = 0; j < sheet.getColumns(); j++) {

			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);//nombre indicador
				Cell cell2 = sheet.getCell(1, i);//operacion
				/*Cell cell3 = sheet.getCell(2, i);//nombre empresa sobre la que se calcula indicador
				Cell cell4 = sheet.getCell(3, i);//año en la cual se calcula indicador*/
				CellType type = cell.getType();

				if (type == CellType.LABEL) {
					
					
					Operacion operacion =new Operacion();
					operacion.setOperacion(cell2.getContents());
					

					Indicador indicador = new Indicador(cell.getContents(),operacion);
					
					
					
					// seteo el nombre del indicador
					indicadores.add(indicador);
					
				/*	 System.out.println("I got a label " +
					  cell.getContents()+cell2.getContents());*/
					 

				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

	}
	
	
	

	public Indicador cargarIndPredefinidos(String nombre, String op) {

		
		Operacion operacion =new Operacion();
		operacion.setOperacion(op);
		Indicador indicador = new Indicador(nombre, operacion);
		indicadores.add(indicador);
		return indicador;
	}
	
	 ArrayList<Cuenta> buscarDeUnaEmpresaTodasSusCuentas(Empresa e) {

		return e.getCuentas();
	}
/*
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
				/*Date ultimaFecha = formatoFecha.parse(c.get(i)
					.getFecha());

				/*if ((ultimaFecha.compareTo(formatoFecha.parse(cuentasEmpresa.get(x)
						.getFecha()))) > 0) {
				if(Integer.valueOf(c.get(i).getFecha())>Integer.valueOf(cuentasEmpresa.get(x).getFecha())){
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
*/
	private int existeLaCuenta(Cuenta c, ArrayList<Cuenta> cuentasEmpresa) {
		for (int i = 0; i < cuentasEmpresa.size(); i++) {
			if (cuentasEmpresa.get(i).getNombre().equals(c.getNombre())) {
				return i;// retorno la posicion donde encuentro la empresa
			}
		}
		return -1;

	}
	public int calcular(Empresa e, String fecha, String nombreIndicador){
		Indicador i=this.buscarIndicador(nombreIndicador);
		Operando operando= parser.parse(i.getOperacion(), indicadores);
		return operando.calcular(e, fecha);			
	}
	

	public Indicador buscarIndicador(String ind) {// busco un indicador de la
		// lista ya sea desde pq es
		// predefinido o pq es de
		// una lista
		
		try{
			for (int i = 0; i < indicadores.size(); i++) {
		
			if (indicadores.get(i).getNombre().equals(ind)) {
				return indicadores.get(i);
			}
		}}catch(Exception e){System.out.println("NO SE ENCONTRO EL INDICADOR");};
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

	

	
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}




	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}


	

}