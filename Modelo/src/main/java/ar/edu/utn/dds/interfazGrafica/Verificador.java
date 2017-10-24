package ar.edu.utn.dds.interfazGrafica;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.CampoVacioException;
import ar.edu.utn.dds.excepciones.ErrorFechaException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Verificador {
    public JPanel panel = new JPanel();
    
    public void mostrarError(String mensaje, String titulo){
    	JOptionPane.showMessageDialog(panel,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarInfo(String mensaje, String titulo){
    	JOptionPane.showMessageDialog(panel, mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void textFieldVacio(TextField campo){
    	if(campo.getText().isEmpty()){
    		throw new CampoVacioException("Textbox vacio");
    	}
    }
    
    public void comboBoxVacio(ComboBox<String> comboBox){
    	if(comboBox.getSelectionModel().isEmpty()){
    		throw new CampoVacioException("Combobox vacio");
    	}
    	if(comboBox.getValue().isEmpty()){
    		throw new CampoVacioException("Combobox vacio");
    	}
    }
    
    public void verificarFecha(String fecha){
    	String fechaSeparada[] = fecha.split("/");
    	if(fechaSeparada.length != 3){
    		throw new ErrorFechaException("No se ingreso un año");
    	}
    	if(Integer.valueOf(fechaSeparada[2]) < 1300){
    		throw new ErrorFechaException("El año debe ser mayor a 1300");
    	}
    	if(Integer.valueOf(fechaSeparada[1]) > 12 || Integer.valueOf(fechaSeparada[1]) < 1){
    		throw new ErrorFechaException("El mes debe estar entre 1 y 12");
    	}
    	if(Integer.valueOf(fechaSeparada[0]) > 31 || Integer.valueOf(fechaSeparada[0]) < 1){
    		throw new ErrorFechaException("El dia debe estar entre 1 y 31");
    	}
    }
}
