package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtInactividad;
    @FXML
    private TextField txtPortapapeles;
    @FXML
    private ComboBox<String> cbExpiracion;
    @FXML
    private PasswordField txtAntigua;
    @FXML
    private PasswordField txtNueva;

    private Stage stage;
    private InactividadController inactividadController;

    public void setInactividadController(InactividadController inactividadController){
        this.inactividadController = inactividadController;
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarComboBox();
        txtInactividad.setText("60");
    }

    private void cargarComboBox(){
        cbExpiracion.getItems().add("7 días");
        cbExpiracion.getItems().add("15 días");
        cbExpiracion.getItems().add("21 días");
        cbExpiracion.getItems().add("30 días");
        cbExpiracion.getItems().add("45 días");
        cbExpiracion.getItems().add("60 días");
    }
    @FXML
    private void guardarCambios() {
        try {
            int nuevoTiempo = Integer.parseInt(txtInactividad.getText()) * 1000; // Convertir a milisegundos

            if (nuevoTiempo > 0 && inactividadController != null) {
                inactividadController.setTiempoInactividad(nuevoTiempo); // Actualizar el tiempo de inactividad
            }
            stage.close(); // Cierra la ventana de configuración
        } catch (NumberFormatException e) {
            // Muestra un mensaje en caso de error en el formato
            alertasController.showError("Error", "Formato Inválido", "Ingrese un valor numérico válido para el tiempo de inactividad.");
            txtInactividad.setText("60"); // Restablecer a 60 si hay error
        }
    }
    @FXML
    private void salir(){
        //Cerrar ventana
        stage.close();
    }
}
