package org.java.passwordmanager.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.Clipboard;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class PortapapelesController {
    private long tiempoLimpiarPortapapeles; // Tiempo de limpieza en milisegundos
    private Timer timer; // Temporizador para el portapapeles
    private boolean mostrarAlertas = true; // Variable para controlar si se deben mostrar alertas

    private final Notifier notifier = new NotificationService();

    public PortapapelesController(long tiempoUsuario) {
        this.tiempoLimpiarPortapapeles = tiempoUsuario > 0 ? tiempoUsuario : 30000; // 30 segundos por defecto
        iniciarTimer();
    }

    // Inicia el temporizador de limpieza del portapapeles
    private void iniciarTimer() {
        if (timer != null) {
            timer.cancel(); // Cancela el temporizador anterior si existe
        }
        timer = new Timer(true); // Crea un nuevo temporizador en modo daemon
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> limpiarPortapapeles());
            }
        }, tiempoLimpiarPortapapeles, tiempoLimpiarPortapapeles); // Ejecuta la tarea repetidamente
    }

    // Limpia el contenido del portapapeles y muestra una alerta si el usuario no ha desactivado las notificaciones
    private void limpiarPortapapeles() {
        // Código para limpiar el portapapeles
        Clipboard.getSystemClipboard().clear(); // Descomentar si usas el portapapeles de JavaFX

        if (mostrarAlertas) {
            mostrarAlertaConConfirmacion();
        }
    }

    // Muestra una alerta con la opción de desactivar futuras alertas
    private void mostrarAlertaConConfirmacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Limpieza de Portapapeles");
        alert.setHeaderText("El contenido del portapapeles ha sido limpiado por seguridad.");
        alert.setContentText("¿Desea seguir recibiendo esta notificación en el futuro?");

        ButtonType buttonYes = new ButtonType("Sí");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonNo) {
            mostrarAlertas = false; // Desactiva las alertas futuras
        }
    }

    // Método para actualizar el tiempo de limpieza configurado por el usuario
    public void setTiempoLimpiarPortapapeles(long nuevoTiempo) {
        this.tiempoLimpiarPortapapeles = nuevoTiempo;
        iniciarTimer(); // Reinicia el temporizador con el nuevo tiempo
    }

    public long getTiempoLimpiarPortapapeles() {
        return tiempoLimpiarPortapapeles;
    }
}
