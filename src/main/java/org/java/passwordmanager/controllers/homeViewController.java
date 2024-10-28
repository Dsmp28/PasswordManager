package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.java.passwordmanager.HelloApplication;

import java.net.URL;
import java.util.ResourceBundle;

public class homeViewController implements Initializable {
    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnNuevo;

    // ESTO ES DEL PANE DE REGISTRO
    @FXML
    private Pane paneRegistro;
    @FXML
    private Button btnSubir;
    @FXML
    private Button btnGuardar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtURL;
    @FXML
    private TextField txtNotas;
    @FXML
    private TextField txtTags;
    @FXML
    private FlowPane tagsPane;

    @FXML
    private TextField txtAdicional1;
    @FXML
    private TextField txtAdicional2;
    @FXML
    private TextField txtAdicional3;
    @FXML
    private TextField txtAdicional4;
    @FXML
    private TextField txtAdicional5;

    //ESTO ES DEL PANE DE PASSWORD (pendiente, no hacer caso pls)
    @FXML
    private Pane panePassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneRegistro.setVisible(false);
        metodoTags();
    }

    @FXML
    private void abrirVentanaRegistro(){
        paneRegistro.setVisible(true);
    }

    private void metodoTags(){
        txtTags.setOnKeyPressed(e -> {
            if(e.getCode().toString().equals("ENTER")){
                tagButton(tagsPane, txtTags.getText());
                txtTags.clear();
            }
        });
    }
    Image image = new Image(getClass().getResource("/org/java/passwordmanager/images/borrar.png").toExternalForm());
    private void tagButton(FlowPane fPane, String tag){
        ImageView close = new ImageView(image);
        fPane.setHgap(5);
        fPane.setVgap(5);
        close.setFitHeight(10);
        close.setFitWidth(10);
        Button result = new Button(tag, close);
        result.setGraphicTextGap(10);
        result.setPrefHeight(20);
        result.setStyle("-fx-background-color: #2c2c2c; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white;");
        result.setContentDisplay(javafx.scene.control.ContentDisplay.RIGHT);
        result.setOnAction(e -> fPane.getChildren().remove(result));
        fPane.getChildren().add(result);
        //Añadir a la lista de tags
    }

    @FXML
    private void guardarRegistro(){
        //Lógica para guardar el registro
        //Cerrar ventana
        paneRegistro.setVisible(false);
    }

    @FXML
    private void subirImagen(){
        //Lógica para subir imagen desde el boton de btnSubir
    }
}
