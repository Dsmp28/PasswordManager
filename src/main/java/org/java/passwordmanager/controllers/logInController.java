package org.java.passwordmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objects.User;

import java.io.File;

public class logInController {
    @FXML
    private Button btnIniciar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnImportar;

    @FXML
    private Label lblArchivo;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnRegistrarse;

    private final Notifier notifier = new NotificationService();

    private File selectedFile;


    @FXML
    private void salir() {
        System.exit(0);
    }

    @FXML
    private void registrarse(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/signInView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 740, 495));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void importar() throws Exception {
        // Configura el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtra para mostrar solo archivos .txt y .enc
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.json)", "*.json");
        FileChooser.ExtensionFilter encFilter = new FileChooser.ExtensionFilter("Archivos encriptados (*.enc)", "*.enc");
        fileChooser.getExtensionFilters().addAll(txtFilter, encFilter);

        // Abre el explorador de archivos y espera la selección
        Stage stage = (Stage) btnImportar.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);
    }


    @FXML
    private void iniciarSesion() {
        archivosController archivosController = new archivosController();
        User user = archivosController.getUser(txtUsuario.getText(), txtPassword.getText());

        if(user == null) {
            notifier.showError("Usuario o contraseña incorrectos, por favor intente de nuevo");
            return;
        }

        Stage stageActual = (Stage) btnIniciar.getScene().getWindow();
        stageActual.close();

        try {
            if (selectedFile != null) {
                importFController importFController = new importFController();
                importFController.importarArchivo(selectedFile);
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/lateralView.fxml"));
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(fxmlLoader.load(), 1040, 495));
            nuevaVentana.initStyle(StageStyle.UNDECORATED);

            // Inicializar controladores de inactividad y portapapeles
            InactividadController inactividadController = new InactividadController(60000, nuevaVentana);
            PortapapelesController portapapelesController = new PortapapelesController(30000);

            lateralViewController controller = fxmlLoader.getController();
            controller.setInactividadController(inactividadController);
            controller.setPortapapelesController(portapapelesController);

            nuevaVentana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

