package org.java.passwordmanager.notifications;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class NotificationService implements Notifier {

    @Override
    public void showSuccess(String message) {
        showAlert(AlertType.INFORMATION, "Éxito", message);
    }

    @Override
    public void showError(String message) {
        showAlert(AlertType.ERROR, "Error", message);
    }

    @Override
    public void showInfo(String message) {
        showAlert(AlertType.INFORMATION, "Información", message);
    }

    @Override
    public void showWarning(String message) {
        showAlert(AlertType.WARNING, "Advertencia", message);
    }

    @Override
    public boolean showDecision(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Personalizar los botones
        ButtonType buttonAceptar = new ButtonType("Aceptar");
        ButtonType buttonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(buttonAceptar, buttonCancelar);

        // Mostrar el cuadro de diálogo y esperar la decisión del usuario
        Optional<ButtonType> result = alert.showAndWait();

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/java/passwordmanager/stylesheets/alertStyles.css").toExternalForm());

        // Retorna true si el usuario seleccionó "Aceptar", de lo contrario, retorna false
        return result.isPresent() && result.get() == buttonAceptar;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/java/passwordmanager/stylesheets/alertStyles.css").toExternalForm());
        alert.showAndWait();
    }
}

