package org.java.passwordmanager.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;

import java.util.Timer;
import java.util.TimerTask;

public class PortapapelesController {
    private long tiempoLimpiarPortapapeles; // Tiempo de limpieza en milisegundos
    private Timer timer; // Temporizador para el portapapeles
    private Alert alert; // Alerta de limpieza

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

    // Limpia el contenido del portapapeles y muestra una alerta
    private void limpiarPortapapeles() {
        // Código para limpiar el portapapeles
        Clipboard.getSystemClipboard().clear(); // Descomentar si usas el portapapeles de JavaFX

        // Muestra una alerta indicando que el portapapeles se ha limpiado
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Limpieza del Portapapeles");
        alert.setHeaderText(null);
        alert.setContentText("El contenido del portapapeles ha sido limpiado por seguridad.");
        alert.show();
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
