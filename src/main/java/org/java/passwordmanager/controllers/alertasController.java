package org.java.passwordmanager.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class alertasController {

    public static void showInformation(String title, String header, String content){
        showAlert(Alert.AlertType.INFORMATION, title, header, content);
    }
    public static void showError(String title, String header, String content){
        showAlert(Alert.AlertType.ERROR, title, header, content);
    }
    public static void showConfirmation(String title, String header, String content){
        showAlert(Alert.AlertType.CONFIRMATION, title, header, content);
    }
    private static void showAlert(Alert.AlertType alertType, String title, String header, String content){
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.setGraphic(null);
            alert.getDialogPane().getStylesheets().add(alertasController.class.getResource("/org/java/passwordmanager/stylesheets/alertasStylesheet.css").toExternalForm());
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.toFront();
            alert.showAndWait();
        });
    }
}
