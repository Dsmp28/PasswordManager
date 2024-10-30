package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.java.passwordmanager.objects.Icon;

import java.net.URL;
import java.util.ResourceBundle;

public class itemController implements Initializable {
    @FXML
    private ImageView igLogo;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbURL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

    public void setItem(String nombre, String url, Icon icon) {
        lbNombre.setText(nombre);
        lbURL.setText(url);
        igLogo.setImage(icon.getImage());
    }
}
