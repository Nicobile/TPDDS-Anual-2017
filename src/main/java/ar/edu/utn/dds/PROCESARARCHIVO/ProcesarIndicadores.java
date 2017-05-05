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

	 ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
     

	void leerExcel() throws IOException  {
       File inputWorkbook = new File(this.getClass().getResource("/Indicadores.xls").getFile());
		
	
        Workbook w=null;
        try {
         w = Workbook.getWorkbook(inputWorkbook);                      
         Sheet sheet = w.getSheet(0);                       
         //for (int j = 0; j < sheet.getColumns(); j++) {
            
        	 for (int i = 0; i < sheet.getRows(); i++) {
                  Cell cell = sheet.getCell(0, i);
                  Cell cell2 = sheet.getCell(1, i);
                   CellType type = cell.getType();
                   Indicador indicador= new Indicador();
                  
                   if(type== CellType.LABEL){
                	   
                	   //seteo el nombre del indicador
                	   indicador.setNombre(cell.getContents());                   
                   
                	   indicador.setOperacion(cell2.getContents());
                	   indicadores.add(indicador);
                        
                        }}
                        
                } catch (BiffException e){e.printStackTrace();}
      
        }
	
	public void cargarIndPredefinidos(String nombre, String operacion){
		Indicador indicador=new Indicador();
		indicador.setNombre(nombre);
		indicador.setOperacion(operacion);
		indicadores.add(indicador);
	 }
       //esto es para probar que funciona leer el excel y meterlo en una lista
        
        public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	

}