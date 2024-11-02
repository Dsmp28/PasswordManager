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
import org.java.passwordmanager.objects.User;

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
