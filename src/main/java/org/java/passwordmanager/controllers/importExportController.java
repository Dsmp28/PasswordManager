package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class importExportController {
    @FXML
    private Button btnSalir;

    @FXML
    private Button btnImportEncriptado;

    @FXML
    private Button btnExportEncriptado;

    @FXML
    private Button btnImportPlano;

    @FXML
    private Button btnExportPlano;


    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void salir(){
        //Cerrar ventana
        stage.close();
    }

    @FXML
    private void importEncriptado(){

    }
    @FXML
    private void exportEncriptado(){

    }

    @FXML
    private void importPlano(){

    }
    @FXML
    private void exportPlano(){

    }
}
