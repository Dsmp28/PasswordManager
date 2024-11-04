package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.java.passwordmanager.HelloApplication;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objectControllers.RegistroController;
import org.java.passwordmanager.objects.CamposExtra;
import org.java.passwordmanager.objects.Icon;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;
import javafx.scene.input.KeyCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class homeViewController implements Initializable {
    private List<Tag> tags;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<String> cbBusqueda;
    @FXML
    private DatePicker dpBuscar;
    @FXML
    private ComboBox<String> cbTags;


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
    private Button btnCopiar;
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

    @FXML
    private VBox vItems;
    //ESTO ES DEL PANE DE PASSWORD (pendiente, no hacer caso pls)
    @FXML
    private Pane panePassword;
    @FXML
    private Button btnAct;
    @FXML
    private Button btnSubirE;
    @FXML
    private ImageView igLogoE;

    @FXML
    private TextField txtNombreE;


    @FXML
    private TextField txtUsuarioE;

    @FXML
    private PasswordField txtPassE;

    @FXML
    private TextField txtURLE;
    @FXML
    private TextField txtNotasE;
    @FXML
    private TextField txtTagsE;
    @FXML
    private FlowPane tagsPaneE;

    @FXML
    private TextField txtAdicional1E;
    @FXML
    private TextField txtAdicional2E;
    @FXML
    private TextField txtAdicional3E;
    @FXML
    private TextField txtAdicional4E;
    @FXML
    private TextField txtAdicional5E;
    @FXML
    private VBox vBEdit;

    @FXML
    private Button btnEditar;

    private final Notifier notifier = new NotificationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegistroController registroController = new RegistroController();
        tags = new ArrayList<>();
        paneRegistro.setVisible(false);
        panePassword.setVisible(false);
        metodoTags(tagsPane, txtTags);
        if(registroController.getSize() > 0){
            inicializarLista();
        }
        cargarCbBusqueda();
        cargarCbTags();
        cbBusqueda.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            resetCampoBusqueda();
            if(newValue.equals("Tags")){
                cbTags.setVisible(true);
                txtBuscar.setVisible(false);
                dpBuscar.setVisible(false);
            }else if(newValue.equals("Fecha de creación") || newValue.equals("Fecha de actualización") || newValue.equals("Fecha de expiración")){
                dpBuscar.setVisible(true);
                txtBuscar.setVisible(false);
                cbTags.setVisible(false);
            }else{
                txtBuscar.setVisible(true);
                dpBuscar.setVisible(false);
                cbTags.setVisible(false);
            }
        });

        cbTags.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                SearchByField();
            }
        });


        //Busqueda con Enter
        txtBuscar.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                SearchByField();
            }
        });

        dpBuscar.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                SearchByField();
            }
        });

        //AGREGAR MÉTODO PARA INICIALIZAR EL CBTAGS DE BÚSQUEDA
    }

    private void inicializarLista(){
        vItems.getChildren().clear();
        try{
            RegistroController registroController = new RegistroController();
            Map<Integer, Registro> registros = registroController.getRegistros();
            Node[] nodes = new Node[registros.size() + 1];

            for(int i = 1; i < registroController.getSize() + 1; i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/org/java/passwordmanager/visuals/itemList.fxml"));
                nodes[i] = loader.load();
                itemController controller = loader.getController();
                controller.setItem(registros.get(i).getSiteName(), registros.get(i).getUrl(), registros.get(i).getIcon());
                final int h = i;
                Registro actual = registroController.getRegistro(h);
                nodes[i].setOnMouseEntered(e -> {
                    //Abrir ventana de password
                    nodes[h].setStyle("-fx-background-color: #2c2c2c;");
                });
                nodes[i].setOnMouseClicked(e -> {

                    bloquearCampos();
                    //DEBUG
                    registroController.mostrarRegistros();
                    //Abrir ventana de password
                    panePassword.setVisible(true);
                    paneRegistro.setVisible(false);
                    vBEdit.setVisible(true);
                    btnAct.setDisable(false);
                    txtNombreE.setText(actual.getSiteName());
                    txtUsuarioE.setText(actual.getUsername());
                    txtPassE.setText(actual.getPassword());
                    txtURLE.setText(actual.getUrl());
                    txtNotasE.setText(actual.getNotes());
                    txtTagsE.setText("");
                    List<Tag> tagsAux = new ArrayList<>(actual.getTags());
                    cargarTagsPane(tagsAux);
                    txtAdicional1E.setText(actual.getCamposExtra().getExtra1());
                    txtAdicional2E.setText(actual.getCamposExtra().getExtra2());
                    txtAdicional3E.setText(actual.getCamposExtra().getExtra3());
                    txtAdicional4E.setText(actual.getCamposExtra().getExtra4());
                    txtAdicional5E.setText(actual.getCamposExtra().getExtra5());
                    igLogoE.setImage(new Image(actual.getIcon().getImagen()));
                    metodoTags(tagsPaneE, txtTagsE);
                });
                nodes[i].setOnMouseExited(e -> {
                    nodes[h].setStyle("-fx-background-color: #1c1c1c;");
                });
                vItems.getChildren().add(nodes[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirVentanaRegistro(){
        paneRegistro.setVisible(true);
        panePassword.setVisible(false);
        //debug, verificar que los tags estén vacíos
        System.out.println(tags.size());
    }

    private void metodoTags(FlowPane tPane, TextField txTag) {
        txTag.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER") && !txTag.getText().isEmpty()) {
                tagButton(tPane, txTag.getText().trim());
                txTag.clear();
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
        tags.add(new Tag(tag));
        fPane.getChildren().add(result);
    }


    @FXML
    private void guardarRegistro(){
        if(verificarCamposObligatorios()){
            try{
                RegistroController registroController = new RegistroController();
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

                Registro registro = new Registro(nombre, usuario, pass, url, notas, camposExtra, new ArrayList<>(tags), creationDate, updateDate, expirationDate, icon);

                registroController.addRegistro(registro);

                for(Tag tag : tags){
                    RegistroController.addTag(tag.getName());
                }

                cargarCbTags();
                registroController.mostrarRegistros();

                inicializarLista();

                notifier.showSuccess("Registro guardado correctamente");
                //Limpiar campos
                limpiarCampos();
            }catch (Exception e){
               notifier.showError("Error al guardar registro");
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
        Icon icon = new Icon("default", 32, 32, getClass().getResource("/org/java/passwordmanager/images/subir.png").toExternalForm());
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
                notifier.showError("Error al subir imagen");
            }
        }else{
            notifier.showError("Error al subir imagen");
        }
    }

    private boolean verificarCamposObligatorios(){
        if(txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtPass.getText().isEmpty() || txtURL.getText().isEmpty() || txtNotas.getText().isEmpty()){
            notifier.showError("Por favor llene todos los campos obligatorios");
            return false;
        }
        return true;
    }
    @FXML
    private void activarCampos(){
        txtNombreE.setDisable(false);
        txtUsuarioE.setDisable(false);
        txtPassE.setDisable(false);
        txtURLE.setDisable(false);
        txtNotasE.setDisable(false);
        txtTagsE.setDisable(false);
        tagsPaneE.setDisable(false);
        txtAdicional1E.setDisable(false);
        txtAdicional2E.setDisable(false);
        txtAdicional3E.setDisable(false);
        txtAdicional4E.setDisable(false);
        txtAdicional5E.setDisable(false);
        btnSubirE.setDisable(false);
        btnEditar.setDisable(false);
    }

    @FXML
    private void bloquearCampos(){
        txtNombreE.setDisable(true);
        txtUsuarioE.setDisable(true);
        txtPassE.setDisable(true);
        txtURLE.setDisable(true);
        txtNotasE.setDisable(true);
        txtTagsE.setDisable(true);
        tagsPaneE.setDisable(true);
        txtAdicional1E.setDisable(true);
        txtAdicional2E.setDisable(true);
        txtAdicional3E.setDisable(true);
        txtAdicional4E.setDisable(true);
        txtAdicional5E.setDisable(true);
        btnSubirE.setDisable(true);
        btnEditar.setDisable(true);
    }
    @FXML
    private void editarRegistro(){
        //CODIGO PARA EDITAR REGISTRO
    }
    @FXML
    private void copiarPass() {
        // Verificar que haya una contraseña en txtPassE
        String password = txtPassE.getText();
        if (password != null && !password.isEmpty()) {
            // Crear el contenido del portapapeles
            ClipboardContent content = new ClipboardContent();
            content.putString(password);

            // Obtener el portapapeles y establecer el contenido
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);

            // Notificar al usuario que se ha copiado
            notifier.showSuccess("Contraseña copiada al portapapeles");
        } else {
            notifier.showError("No hay contraseña para copiar");
        }
    }
    private void cargarTagsPane(List<Tag> tagsAux) {
        tagsPaneE.getChildren().clear();
        for (Tag tag : tagsAux) {
            tagButton(tagsPaneE, tag.getName());
        }
    }
    private void cargarCbBusqueda(){
        cbBusqueda.getItems().add("Nombre");
        cbBusqueda.getItems().add("Usuario");
        cbBusqueda.getItems().add("URL");
        cbBusqueda.getItems().add("Notas");
        cbBusqueda.getItems().add("Campos extra");
        cbBusqueda.getItems().add("Fecha de creación");
        cbBusqueda.getItems().add("Fecha de actualización");
        cbBusqueda.getItems().add("Fecha de expiración");
        cbBusqueda.getItems().add("Tags");
    }
    private void cargarCbTags(){
        cbTags.getItems().clear();
        for(String tag : RegistroController.getTags()){
            cbTags.getItems().add(tag);
        }
    }
    private void resetCampoBusqueda(){
        txtBuscar.clear();
        dpBuscar.setValue(null);
        cbTags.setValue(null);
    }

    private void SearchByField(){
        String campo = cbBusqueda.getValue();
        List<Registro> resultados = new ArrayList<>();

        switch (campo) {
            case "Nombre":
              resultados = RegistroController.searchBySiteName(txtBuscar.getText());
              coincidenciasEncontrada(resultados);
              break;
            case "Usuario":
                resultados = RegistroController.searchByUsername(txtBuscar.getText());
                coincidenciasEncontrada(resultados);
                break;
            case "URL":
                resultados = RegistroController.searchByURL(txtBuscar.getText());
                coincidenciasEncontrada(resultados);
            case "Notas":
                resultados = RegistroController.searchByNotes(txtBuscar.getText());
                coincidenciasEncontrada(resultados);
                break;
            case "Campos extra":
                resultados = RegistroController.searchByExtraField(txtBuscar.getText());
                coincidenciasEncontrada(resultados);
                break;
            case "Fecha de creación":
                resultados = RegistroController.searchByCreationDate(dpBuscar.getValue());
                coincidenciasEncontrada(resultados);
                break;
            case "Fecha de actualización":
                resultados = RegistroController.searchByUpdateDate(dpBuscar.getValue());
                coincidenciasEncontrada(resultados);
                break;
            case "Fecha de expiración":
                resultados = RegistroController.searchByExpirationDate(dpBuscar.getValue());
                coincidenciasEncontrada(resultados);
                break;
            case "Tags":
                resultados = RegistroController.searchByTag(cbTags.getValue());
                inicializarListaRegistrosEncontrados(resultados);
                break;
            default:
                inicializarLista(); //Mostrar todos los registros
                break;
        }
    }

    private void coincidenciasEncontrada(List<Registro> resultados){
        resetCampoBusqueda();
        cbBusqueda.setValue("");
        RegistroController.mostrarRegistrosEncontrados(resultados);
        inicializarListaRegistrosEncontrados(resultados);
    }

    private void inicializarListaRegistrosEncontrados(List<Registro> registros) {
        vItems.getChildren().clear();
        try {
            Node[] nodes = new Node[registros.size()];

            for (int i = 0; i < registros.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/org/java/passwordmanager/visuals/itemList.fxml"));
                nodes[i] = loader.load();

                itemController controller = loader.getController();
                Registro actual = registros.get(i);
                controller.setItem(actual.getSiteName(), actual.getUrl(), actual.getIcon());

                final int h = i;
                nodes[i].setOnMouseEntered(e -> {
                    nodes[h].setStyle("-fx-background-color: #2c2c2c;");
                });
                nodes[i].setOnMouseClicked(e -> {
                    panePassword.setVisible(true);
                    vBEdit.setVisible(true);
                    btnAct.setDisable(false);
                    txtNombreE.setText(actual.getSiteName());
                    txtUsuarioE.setText(actual.getUsername());
                    txtPassE.setText(actual.getPassword());
                    txtURLE.setText(actual.getUrl());
                    txtNotasE.setText(actual.getNotes());
                    List<Tag> tagsAux = new ArrayList<>(actual.getTags());
                    cargarTagsPane(tagsAux);
                    txtAdicional1E.setText(actual.getCamposExtra().getExtra1());
                    txtAdicional2E.setText(actual.getCamposExtra().getExtra2());
                    txtAdicional3E.setText(actual.getCamposExtra().getExtra3());
                    txtAdicional4E.setText(actual.getCamposExtra().getExtra4());
                    txtAdicional5E.setText(actual.getCamposExtra().getExtra5());
                    igLogoE.setImage(new Image(actual.getIcon().getImagen()));
                });
                nodes[i].setOnMouseExited(e -> {
                    nodes[h].setStyle("-fx-background-color: #1c1c1c;");
                });

                vItems.getChildren().add(nodes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
