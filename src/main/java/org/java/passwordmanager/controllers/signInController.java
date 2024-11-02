package org.java.passwordmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;

public class signInController {
    @FXML
    private Button btnIniciar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtEncriptacion;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnRegresar;

    private final Notifier notifier = new NotificationService();


    @FXML
    private void salir(){
        System.exit(0);
    }

    @FXML
    private void registrarse() {
        try {
            if (!validarCampos()) return;

            archivosController archivosController = new archivosController();
            String username = txtUsuario.getText();
            String password = txtPassword.getText();
            String encriptacion = txtEncriptacion.getText();

            if (archivosController.isUserRegistered(username)) {
                notifier.showError("El usuario ya está registrado, por favor escriba otro nombre de usuario");
                return;
            }

            if (archivosController.isAlreadyDesKey() && !confirmarSobreescritura()) return;

            archivosController.saveUserData(username, password);
            archivosController.saveDesData(encriptacion);
            notifier.showSuccess("Usuario y clave de encriptacion registrados correctamente");
            limpiarCampos();

        } catch (Exception e) {
            notifier.showError("Ocurrió un error al registrar el usuario");
        }
    }

    // Método para validar campos obligatorios y longitud de clave de encriptación
    private boolean validarCampos() {
        String username = txtUsuario.getText();
        String password = txtPassword.getText();
        String encriptacion = txtEncriptacion.getText();

        if (username.isEmpty() || password.isEmpty() || encriptacion.isEmpty()) {
            notifier.showError("Por favor llene todos los campos");
            return false;
        }

        if (encriptacion.length() != 8) {
            notifier.showError("La clave de encriptación debe tener 8 caracteres, por favor intente de nuevo");
            return false;
        }

        return true;
    }

    // Método para confirmar si se debe sobreescribir la clave de encriptación
    private boolean confirmarSobreescritura() {
        return notifier.showDecision("Ya existe una clave de encriptación, ¿desea sobreescribirla (si elige cancelar no se guardara su usuario)?");
    }

    // Método para limpiar los campos de entrada
    private void limpiarCampos() {
        txtUsuario.clear();
        txtPassword.clear();
        txtEncriptacion.clear();
    }


    @FXML
    private void regresar(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/logInView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 740, 495));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
