package ar.edu.utn.dds.interfazGrafica;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.utn.dds.excepciones.CampoVacioException;
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
}
