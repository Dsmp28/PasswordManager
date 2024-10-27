package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class signInController {
    @FXML
    private Button btnIniciar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;

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
        //Abrir ventana de registro
    }
    @FXML
    private void iniciarSesion(){
        //Iniciar sesión
    }
}
