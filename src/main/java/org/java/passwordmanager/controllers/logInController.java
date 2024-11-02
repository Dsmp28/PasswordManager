package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class logInController {
    @FXML
    private Button btnIniciar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private void salir(){
        System.exit(0);
    }
    @FXML
    private void registrarse(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/signInView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 740, 495));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void iniciarSesion(){
        //Validar usuario y contraseña
        //Abrir ventana principal
        Stage stageActual = (Stage) btnIniciar.getScene().getWindow(); // Obtiene el Stage actual
        stageActual.close(); // Cierra la ventana actual

        // Abrir la ventana principal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/lateralView.fxml"));
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(fxmlLoader.load(), 1040, 495));
            nuevaVentana.initStyle(StageStyle.UNDECORATED);

            // Inicializar el controlador de inactividad
            InactividadController inactividadController = new InactividadController(60000, nuevaVentana); // 1 minuto por defecto

            // Pasar la instancia de InactividadController al lateralViewController
            lateralViewController controller = fxmlLoader.getController();
            controller.setInactividadController(inactividadController); // Establece el controlador de inactividad

            nuevaVentana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
