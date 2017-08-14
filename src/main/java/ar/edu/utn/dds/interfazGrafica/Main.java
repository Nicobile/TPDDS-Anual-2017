package ar.edu.utn.dds.interfazGrafica;

import java.io.IOException;

import ar.edu.utn.dds.modelo.Traductor;
import ar.edu.utn.dds.procesarArchivos.LectorArchivo;
import ar.edu.utn.dds.procesarArchivos.ProcesarIndicadores;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

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
			controller.setStageMenuPrincipal(stagePrincipal);
			stagePrincipal.show();
		} catch (IOException e) {
		}
	}

	public void mostrarMenuMetodologias() {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/MenuMetodologias.fxml"));
			BorderPane ventanaMeto = (BorderPane) loader.load();
			Scene scene = new Scene(ventanaMeto);
			Stage ventanaMetologia = new Stage();
			ventanaMetologia.setTitle("Menu");
			ventanaMetologia.setScene(scene);
			MenuMetodologias controller = loader.getController();
			controller.setTraductor(traductor);
			controller.setStagePrincipalMeto(ventanaMetologia);
			ventanaMetologia.show();
		} catch (IOException e) {
		}
	}

	public void menuIndicadores() {
		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("/ar/edu/utn/dds/interfazGrafica/MenuIndicadores.fxml"));
			BorderPane ventanaCond3 = (BorderPane) loader.load();
			Stage ventana3 = new Stage();
			ventana3.setTitle("Indicadores");
			ventana3.initOwner(stagePrincipal);
			Scene scene = new Scene(ventanaCond3);
			ventana3.setScene(scene);
			MenuIndicadores controller = loader.getController();
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

	public static void main(String[] args) {
		launch(args);

	}

}
