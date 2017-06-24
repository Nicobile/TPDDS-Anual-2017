package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;

import ar.edu.utn.dds.controller.Condicion1;
import ar.edu.utn.dds.controller.Condicion2;
import ar.edu.utn.dds.controller.Condicion3;
import ar.edu.utn.dds.controller.MainGraf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class Main extends Application {
	//@Override
	//public void start(Stage primaryStage) throws Exception{
		//Parent root = FXMLLoader.load(getClass().getResource("/ar/edu/utn/dds/interfazGrafica/Menu.fxml"));
		//Scene scene = new Scene(root);
		//primaryStage.setScene(scene);
		//primaryStage.show();
	private Stage stagePrincipal;
	private BorderPane rootPane;

	@Override
	public void start(Stage stagePrincipal) throws Exception {
		   this.stagePrincipal = stagePrincipal;
		   mostrarVentanaPrincipal();
	}

	
	 /*
     * cargamos la ventana principal
     */
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Menu.fxml"));
            rootPane=(BorderPane) loader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setTitle("Menu");
            stagePrincipal.setScene(scene);
            MainGraf controller = loader.getController();
            controller.setProgramaPrincipal(this);
            stagePrincipal.show();
        } 
        catch (IOException e) {
        }
    }
    public void mostrarCondicion1() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Condicion1.fxml"));
            BorderPane ventanaDos = (BorderPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Condicion 1");
            ventana.initOwner(stagePrincipal);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            Condicion1 controller = loader.getController();
            controller.setStagePrincipal(ventana);
            ventana.show();

        } 
        catch (Exception e) {
        }
    }
    
    
    //abre la ventana condicion 2    
    public void mostrarCondicion2() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Condicion2.fxml"));
            BorderPane ventanaDos = (BorderPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Condicion 2");
            ventana.initOwner(stagePrincipal);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            Condicion2 controller = loader.getController();
            controller.setStagePrincipal2(ventana);
            ventana.show();

        } 
        catch (Exception e) {
        }
    }
    
    public void mostrarCondicion3() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Condicion3.fxml"));
            BorderPane ventanaCond3 = (BorderPane) loader.load();
            Stage ventana3 = new Stage();
            ventana3.setTitle("Condicion 3");
            ventana3.initOwner(stagePrincipal);
            Scene scene = new Scene(ventanaCond3);
            ventana3.setScene(scene);
            Condicion3 controller = loader.getController();
            controller.setStagePrincipal3(ventana3);
            ventana3.show();

        } 
        catch (Exception e) {
        }
    }
	
	public static void main(String[] args){
		launch(args);
		
	}
}
