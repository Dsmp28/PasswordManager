package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objectControllers.RegistroController;

import java.io.File;

public class importExportController {
    @FXML
    private Button btnSalir;

    @FXML
    private Button btnImportEncriptado;

    @FXML
    private Button btnExportEncriptado;

    @FXML
    private Button btnImportPlano;

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
    private void importEncriptado(){
        try {
            File file = selectFile("enc");
            importFController importFController = new importFController();
            if (file == null) {
                notifier.showError("No se seleccionó ningún archivo");
                return;
            }
            importFController.importarArchivo(file);
            notifier.showSuccess("Archivo importado correctamente");
            stage.close();
        } catch (Exception e) {
            notifier.showError("Error al importar archivo");
        }
    }
    @FXML
    private void exportEncriptado(){
        try {
            exportFController exportFController = new exportFController();
            exportFController.exportRegistros(RegistroController.getRegistros(), "src/main/resources/org/java/passwordmanager/dataFiles/salidaEncriptada.enc", "enc");
            notifier.showSuccess("Archivo exportado correctamente. La ruta del archivo es src/main/resources/org/java/passwordmanager/dataFiles/salidaEncriptada.enc");
            stage.close();
        } catch (Exception e) {
            notifier.showError("No se pudo exportar el archivo");
        }
    }

    @FXML
    private void importPlano() throws Exception {
        try {
            File file = selectFile("json");
            importFController importFController = new importFController();
            importFController.importarArchivo(file);
            notifier.showSuccess("Archivo importado correctamente");
        } catch (Exception e) {
            notifier.showError("Error al importar archivo");
        }
    }
    @FXML
    private void exportPlano(){
        try {
            exportFController exportFController = new exportFController();
            exportFController.exportRegistros(RegistroController.getRegistros(), "src/main/resources/org/java/passwordmanager/dataFiles/salidaNormal.json", "json");
            notifier.showSuccess("Archivo exportado correctamente. La ruta del archivo es src/main/resources/org/java/passwordmanager/dataFiles/salidaNormal.json");
            stage.close();
        } catch (Exception e) {
            notifier.showError("No se pudo exportar el archivo");
        }
    }

    private File selectFile(String type){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtra para mostrar solo archivos .txt y .enc
        if (type.equals("json")) {
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(txtFilter);
        } else if (type.equals("enc")) {
            FileChooser.ExtensionFilter encFilter = new FileChooser.ExtensionFilter("Archivos encriptados (*.enc)", "*.enc");
            fileChooser.getExtensionFilters().add(encFilter);
        }

        // Abre el explorador de archivos y espera la selección
        Stage stage = (Stage) btnImportPlano.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }
}
