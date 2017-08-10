package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage stagePrincipal;
	private BorderPane rootPane;
	private Traductor traductor;
	private ProcesarIndicadores procesador1;
	private LectorArchivo lectorArchivo;
	private Archivos archivos;

	@Override
	public void start(Stage stagePrincipal) throws Exception {
		this.stagePrincipal = stagePrincipal;
		traductor = new Traductor();
		archivos = new Archivos();
		procesador1 = new ProcesarIndicadores(traductor);
		lectorArchivo = new LectorArchivo(traductor);
		mostrataMenuPrincipal();
	}

	public void mostrataMenuPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/MenuPrincipal.fxml"));
			rootPane = (BorderPane) loader.load();
			Scene scene = new Scene(rootPane);
			stagePrincipal.setTitle("Menu Princpal");
			stagePrincipal.setScene(scene);
			MenuPrincipal controller = loader.getController();
			controller.setProgramaPrincipal(this);
			stagePrincipal.show();
		} catch (IOException e) {
		}
	}

	public void mostrarVentanaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/Menu.fxml"));
			rootPane = (BorderPane) loader.load();
			Scene scene = new Scene(rootPane);
			stagePrincipal.setTitle("Menu");
			stagePrincipal.setScene(scene);
			MainGraf controller = loader.getController();
			controller.setTraductor(traductor);
			controller.setProgramaPrincipal(this);

			stagePrincipal.show();
		} catch (IOException e) {
		}
	}

	public void menuInterfazIndicadores() {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/InterfazIndicadores.fxml"));
			BorderPane ventanaCond3 = (BorderPane) loader.load();
			Stage ventana3 = new Stage();
			ventana3.setTitle("Indicadores");
			ventana3.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCond3);
			ventana3.setScene(scene);
			InterfazIndicador controller = loader.getController();
			controller.setTraductor(traductor);
			controller.setProcesador(procesador1);
			controller.setListaArchivos(archivos);
			controller.setStagePrincipalInd(ventana3);
			ventana3.show();
		} catch (IOException e) {
		}
	}

	public void menuCuentas() {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/MenuCuentas.fxml"));
			BorderPane ventanaCond3 = (BorderPane) loader.load();
			Stage ventanaCta = new Stage();
			ventanaCta.setTitle("Empresas Y Cuentas");
			ventanaCta.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCond3);
			ventanaCta.setScene(scene);
			MenuCuentas controller = loader.getController();
			controller.setTraductor(traductor);
			controller.setLectorArchivo(lectorArchivo);
			controller.setListaArchivos(archivos);
			controller.setStagePrincipalCuenta(ventanaCta);
			ventanaCta.show();
		} catch (IOException e) {
		}
	}

	/* CONDICIONES */
	
	


	public void mostrarCondicionLongevidad(Metodologia metod) {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/CondicionLongevidad.fxml"));
			BorderPane ventanaCond3 = (BorderPane) loader.load();
			Stage ventana4 = new Stage();
			ventana4.setTitle("Longevidad");
			ventana4.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCond3);
			ventana4.setScene(scene);
			CondicionLongevidad controller = loader.getController();
			controller.setMetodologia(metod);
			controller.setStagePrincipal(ventana4);
			ventana4.show();
		} catch (Exception e) {
		}
	}
	
	public void mostrarCondicionCreciente(Metodologia metod) {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/CondicionCreciente.fxml"));
			BorderPane ventanaCondCrec = (BorderPane) loader.load();
			Stage ventana5 = new Stage();
			ventana5.setTitle("CondCreciente");
			ventana5.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCondCrec);
			ventana5.setScene(scene);
			CondicionCreciente controller = loader.getController();
			controller.setMetodologia(metod);
			controller.setStagePrincipal3(ventana5);
			ventana5.show();
		} catch (Exception e) {
		}
	}
	public void mostrarCondicionDecreciente(Metodologia metod) {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/CondicionDecreciente.fxml"));
			BorderPane ventanaCondCrecDec = (BorderPane) loader.load();
			Stage ventana5 = new Stage();
			ventana5.setTitle("CondDecreciente");
			ventana5.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCondCrecDec);
			ventana5.setScene(scene);
			CondicionDecreciente controller = loader.getController();
			controller.setMetodologia(metod);
			controller.setStagePrincipal3(ventana5);
			ventana5.show();
		} catch (Exception e) {
		}
	}
public void mostrarCondicionSumatoria(Metodologia metod)  {
		

		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/CondicionSumatoria.fxml"));
		
		BorderPane ventanaSumatoria;
		try {
			ventanaSumatoria = (BorderPane) loader.load();
		
		Stage ventana6 = new Stage();
		ventana6.setTitle("CondSumatoria");
		ventana6.initOwner(stagePrincipal);
		Scene scene = new Scene(ventanaSumatoria);
		ventana6.setScene(scene);
		CondicionSumatoria controller = loader.getController();
		controller.setMetodologia(metod);
		controller.setStagePrincipal(ventana6);
		
		ventana6.show();}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

public void mostrarCondicionPromedio(Metodologia metod)  {
	

	FXMLLoader loader = new FXMLLoader(
			Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/CondicionPromedio.fxml"));
	
	BorderPane ventanaPromedio;
	try {
		ventanaPromedio = (BorderPane) loader.load();
	
	Stage ventana6 = new Stage();
	ventana6.setTitle("CondSumatoria");
	ventana6.initOwner(stagePrincipal);
	Scene scene = new Scene(ventanaPromedio);
	ventana6.setScene(scene);
	CondicionPromedio controller = loader.getController();
	controller.setMetodologia(metod);
	controller.setStagePrincipal(ventana6);
	ventana6.show();}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

}

	public static void main(String[] args) {
		launch(args);

	}

	

	// @Override
	// public void start(Stage primaryStage) throws Exception{
	// Parent root =
	// FXMLLoader.load(getClass().getResource("/ar/edu/utn/dds/interfazGrafica/Menu.fxml"));
	// Scene scene = new Scene(root);
	// primaryStage.setScene(scene);
	// primaryStage.show();
}
