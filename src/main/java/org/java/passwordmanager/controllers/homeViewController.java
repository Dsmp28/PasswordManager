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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java.passwordmanager.HelloApplication;
import org.java.passwordmanager.objectControllers.RegistroController;
import org.java.passwordmanager.objects.CamposExtra;
import org.java.passwordmanager.objects.Icon;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class homeViewController implements Initializable {
    private List<Tag> tags;
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
    private ImageView igLogo;
    @FXML
    private Button btnGuardar;

    @FXML
    private TextField txtNombre;


    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPass;

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
        tags = new ArrayList<>();
        paneRegistro.setVisible(false);
        metodoTags();
    }

    @FXML
    private void abrirVentanaRegistro(){
        paneRegistro.setVisible(true);
        //debug, verificar que los tags estén vacíos
        System.out.println(tags.size());
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
        result.setOnAction(e -> {
            fPane.getChildren().remove(result);
            tags.remove(new Tag(tag));
        });
        if(!txtTags.getText().isEmpty()){
            fPane.getChildren().add(result);
            tags.add(new Tag(tag));
        }else{
            //No se agrega el tag
        }
    }

    @FXML
    private void guardarRegistro(){
        if(verificarCamposObligatorios()){
            try{
                //Guardar registro
                Icon icon = new Icon(txtNombre.getText(), 32, 32, igLogo.getImage().getUrl());
                String nombre = txtNombre.getText();
                String usuario = txtUsuario.getText();
                String pass = txtPass.getText();
                String url = txtURL.getText();
                String notas = txtNotas.getText();
                //Ya está creada la lista tags, esta se limpia cuando se limpian los demás campos.
                String adicional1 = txtAdicional1.getText();
                String adicional2 = txtAdicional2.getText();
                String adicional3 = txtAdicional3.getText();
                String adicional4 = txtAdicional4.getText();
                String adicional5 = txtAdicional5.getText();

                //Insertar en la lista global de registros
                CamposExtra camposExtra = new CamposExtra(adicional1, adicional2, adicional3, adicional4, adicional5);
                LocalDateTime creationDate = LocalDateTime.now(); // Por tiempo se pone aqui pero mejor en constructor de Registro
                LocalDateTime updateDate = LocalDateTime.now(); // Por tiempo se pone aqui pero mejor en constructor de Registro
                LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); //Por defecto 30 días pero puede editarse despues - // Por tiempo se pone aqui pero mejor en constructor de Registro
                Registro registro = new Registro(nombre, usuario, pass, url, notas, camposExtra, tags, creationDate, updateDate, expirationDate, icon);
                RegistroController.addRegistro(registro);
                RegistroController.mostrarRegistros();


                alertasController.showInformation("Guardado", "Registro guardado", "El registro se guardó correctamente");
                //Limpiar campos
                limpiarCampos();
            }catch (Exception e){
               alertasController.showError("Error", "Error al guardar", "Ocurrió un error al guardar el registro");
            }
        }
        //Cerrar ventana
        paneRegistro.setVisible(false);
    }

    private void limpiarCampos(){
        txtNombre.clear();
        txtUsuario.clear();
        txtPass.clear();
        txtURL.clear();
        txtNotas.clear();
        txtTags.clear();
        txtAdicional1.clear();
        txtAdicional2.clear();
        txtAdicional3.clear();
        txtAdicional4.clear();
        txtAdicional5.clear();
        tags.clear();
        Icon icon = new Icon("default", 32, 32, getClass().getResource("/org/java/passwordmanager/images/subImg.png").toExternalForm());
        igLogo.setImage(new Image(icon.getImagen()));
        tagsPane.getChildren().clear();
    }
    @FXML
    private void subirImagen(){
        //Subir imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png", "*.jpeg"));
        Stage stage = (Stage) btnSubir.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try{
                BufferedImage bufferedImage = ImageIO.read(file);
                int width = 32;
                int height = 32;
                // Icon icon = new Icon(file.getName(), height, width, file.getAbsolutePath());
                igLogo.setImage(new Image(file.toURI().toString()));

            }catch (Exception e){
                alertasController.showError("Error", "Error al subir imagen", "Ocurrió un error al subir la imagen");
            }
        }else{
            alertasController.showError("Error", "Error al subir imagen", "No se seleccionó ninguna imagen");
        }
    }

    private boolean verificarCamposObligatorios(){
        if(txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtPass.getText().isEmpty() || txtURL.getText().isEmpty() || txtNotas.getText().isEmpty()){
            alertasController.showError("Error", "Campos obligatorios", "Por favor llene todos los campos obligatorios");
            return false;
        }
        return true;
    }
}
