package ar.edu.utn.dds.PROCESARARCHIVO;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcesarIndicadores {

	ArrayList<String> indicadores = new ArrayList<String>();
     

	ArrayList<String> leerExcel() throws IOException  {
       File inputWorkbook = new File(this.getClass().getResource("/Indicadores.xls").getFile());
        Workbook w=null;
        try {
         w = Workbook.getWorkbook(inputWorkbook);                      
         Sheet sheet = w.getSheet(0);                       
         for (int j = 0; j < sheet.getColumns(); j++) {
             for (int i = 0; i < sheet.getRows(); i++) {
                  Cell cell = sheet.getCell(j, i);
                   CellType type = cell.getType();
                   if (type == CellType.LABEL) {
                   
                        indicadores.add(cell.getContents());
                                        	
                       }
                                        /*
                               if (type == CellType.NUMBER) {System.out.println("Numero " + cell.getContents());
                                        }*/

                        }
                        }
                        
                } catch (BiffException e){e.printStackTrace(); }
      
				return indicadores;
        }
	
	public void cargarIndPredefinidos(String a){
		 indicadores.add(a);
	 }
       //esto es para probar que funciona leer el excel y meterlo en una lista
        
        public ArrayList<String> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<String> indicadores) {
		this.indicadores = indicadores;
	}

	/*	public  static void main(String[] args) throws IOException {
        	ArrayList<String> lista = new ArrayList<String>();
                ProcesarIndicadores test = new ProcesarIndicadores();
                
               lista=test.leerExcel();
               System.out.println(lista.get(0));
               System.out.println(lista.get(1));
               
                
        } */

}