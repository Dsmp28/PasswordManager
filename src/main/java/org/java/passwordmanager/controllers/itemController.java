package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.java.passwordmanager.objectControllers.RegistroController;
import org.java.passwordmanager.objects.Icon;
import org.java.passwordmanager.objects.Registro;

import java.net.URL;
import java.util.ResourceBundle;

public class itemController implements Initializable {

    private RegistroController registroController;
    private Registro registro;
    @FXML
    private ImageView igLogo;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbURL;
    @FXML
    private CheckBox chbSeleccion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbSeleccion.setOnAction(event -> {
            manejarSeleccion();
        });
    }

    public void setItem(String nombre, String url, Icon icon) {
        lbNombre.setText(nombre);
        lbURL.setText(url);
        igLogo.setImage(icon.getImage());
    }

    public void setRegistro(Registro registro){
        this.registro = registro;
    }
    public void manejarSeleccion(){
        if (chbSeleccion.isSelected()) {
            registroController.addRegistroSeleccionado(registro);
        } else {
            registroController.removeRegistroSeleccionado(registro);
        }
    }
    public void setRegistroController(RegistroController registroController){
        this.registroController = registroController;
    }
}
