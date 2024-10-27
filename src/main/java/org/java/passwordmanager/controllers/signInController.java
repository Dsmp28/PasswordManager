package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private void salir(){
        System.exit(0);
    }
    @FXML
    private void registrarse(){
        //Abrir ventana de registro
    }
}
