package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class importExportController {
    @FXML
    private Button btnSalir;

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void salir(){
        //Cerrar ventana
        stage.close();
    }
}
