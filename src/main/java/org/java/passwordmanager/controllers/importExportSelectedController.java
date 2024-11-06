package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objectControllers.RegistroController;

import java.io.File;

public class importExportSelectedController {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnExportEncriptado;

    @FXML
    private Button btnExportPlano;


    private Stage stage;

    private final Notifier notifier = new NotificationService();

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void salir(){
        //Cerrar ventana
        stage.close();
    }

    @FXML
    private void exportEncriptado(){
        try {
            exportFController exportFController = new exportFController();
            exportFController.exportRegistrosSeleccionados(RegistroController.getRegistrosSeleccionados(), "src/main/resources/org/java/passwordmanager/dataFiles/salidaSeleccionadosEncriptada.enc", "enc");
            notifier.showSuccess("Archivo exportado correctamente. La ruta del archivo es src/main/resources/org/java/passwordmanager/dataFiles/salidaSeleccionadosEncriptada.enc");
            stage.close();
        } catch (Exception e) {
            notifier.showError("No se pudo exportar el archivo");
        }
    }

    @FXML
    private void exportPlano(){
        try {
            exportFController exportFController = new exportFController();
            exportFController.exportRegistrosSeleccionados(RegistroController.getRegistrosSeleccionados(), "src/main/resources/org/java/passwordmanager/dataFiles/salidaSeleccionadosNormal.json", "json");
            notifier.showSuccess("Archivo exportado correctamente. La ruta del archivo es src/main/resources/org/java/passwordmanager/dataFiles/salidaSeleccionadosNormal.json");
            stage.close();
        } catch (Exception e) {
            notifier.showError("No se pudo exportar el archivo");
        }
    }

}
