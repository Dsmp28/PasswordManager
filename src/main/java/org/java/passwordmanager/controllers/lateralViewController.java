package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class lateralViewController implements Initializable {
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnImportExport;
    @FXML
    private Button btnAjustes;
    @FXML
    private Button btnSalir;

    @FXML
    private StackPane contentArea;
    private InactividadController inactividadController;
    private PortapapelesController portapapelesController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activeButton(btnInicio, "homeView.fxml");
        configurarEventosDeActividad();
    }
    public void setInactividadController(InactividadController inactividadController) {
        this.inactividadController = inactividadController; //
    }
    public void setPortapapelesController(PortapapelesController portapapelesController) {
        this.portapapelesController = portapapelesController;
    }
    private void configurarEventosDeActividad() {
        // Agregar filtros de eventos para detectar actividad del mouse y teclado
        contentArea.addEventFilter(MouseEvent.MOUSE_MOVED, event -> inactividadController.setTiempoInactividad(inactividadController.getTiempoInactividad()));
        contentArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> inactividadController.setTiempoInactividad(inactividadController.getTiempoInactividad()));
    }
    private void reiniciarInactividad() {
        if (inactividadController != null) {
            inactividadController.setTiempoInactividad(inactividadController.getTiempoInactividad());
        }
    }
    @FXML
    private void activeButton(Button activo, String fxml){
        //reiniciarEstilos();
        //activo.setStyle("-fx-background-color: #2c2c2c");
        cargarFxml(fxml);
    }
    private void cargarFxml(String fxml) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/" + fxml));
            Parent root = fxmlLoader.load();
            if(fxmlLoader.getController() instanceof paneController){
                paneController controller = fxmlLoader.getController();
                controller.setDashboardController(this);
            }
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void reiniciarEstilos(){
        btnInicio.setStyle("-fx-background-color: #2c2c2c");
        btnImportExport.setStyle("-fx-background-color: #2c2c2c");
        btnAjustes.setStyle("-fx-background-color: #2c2c2c");
    }

    @FXML
    public void btnInicio(){
        activeButton(btnInicio, "homeView.fxml");
    }
    @FXML
    public void btnImportExport(){
        reiniciarInactividad(); // Reiniciar inactividad al hacer clic en el botón
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/importExportView.fxml"));
            Parent root = loader.load();
            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Importar/Exportar");

            // Configurar la escena y el controlador de la ventana emergente
            emergente.setScene(new Scene(root));
            importExportController controller = loader.getController();
            controller.setStage(emergente);
            emergente.showAndWait();
            cargarFxml("homeView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void btnAjustes(){
        reiniciarInactividad(); // Reiniciar inactividad al hacer clic en el botón
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/settingsView.fxml"));
            Parent root = loader.load();
            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Ajustes");

            // Configurar el controller de settings
            settingsController controller = loader.getController();
            controller.setInactividadController(inactividadController);

            // Obtener el tiempo del portapapeles y pasarlo al settingsController
            long tiempoPortapapeles = portapapelesController != null ? portapapelesController.getTiempoLimpiarPortapapeles() : 30000; // 30 segundos por defecto
            controller.setPortapapelesController(new PortapapelesController(tiempoPortapapeles)); // Nueva instancia del controlador de portapapeles

            emergente.setScene(new Scene(root));
            controller.setStage(emergente);
            emergente.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML

    public void btnSalir(){
        salir();
    }
    private void salir(){
        System.exit(0); //Editar con una confirmación
    }

}
