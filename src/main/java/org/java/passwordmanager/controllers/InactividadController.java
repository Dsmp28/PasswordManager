package org.java.passwordmanager.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Alert;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;

public class InactividadController {

    private long tiempoInactividad; // Tiempo de inactividad en milisegundos
    private Timer timer; // Temporizador para manejar la inactividad
    private Stage stage; // Ventana actual para gestionar la navegación
    private Alert alert; // Referencia a la alerta de inactividad

    private final Notifier notifier = new NotificationService();

    private static final long TIEMPO_ALERTA = 5000; // Tiempo de visualización de la alerta en milisegundos

    public InactividadController(long tiempoUsuario, Stage stage) {
        this.tiempoInactividad = tiempoUsuario > 0 ? tiempoUsuario : 60000; // 1 minuto por defecto
        this.stage = stage;
        iniciarTimer();
    }

    // Método para inicializar el temporizador
    private void iniciarTimer() {
        timer = new Timer(true); // Temporizador en modo daemon
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> mostrarAlertaInactividad());
            }
        }, tiempoInactividad);
    }

    // Verifica la inactividad y muestra la alerta de advertencia
    private void mostrarAlertaInactividad() {
// Mostrar alerta de inactividad
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta de Inactividad");
        alert.setHeaderText(null);
        alert.setContentText("La aplicación se bloqueará en 5 segundos.");
        alert.show(); // Muestra la alerta

        // Configurar un timer para cerrar la sesión automáticamente después del tiempo de alerta
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                bloquearYRegresar(); // Cierra la sesión automáticamente
            }
        }, TIEMPO_ALERTA);
    }

    // Cierra la ventana actual y regresa a la pantalla principal
    private void bloquearYRegresar() {
        Platform.runLater(() -> {
            // Cierra la alerta si está abierta
            if (alert != null) {
                alert.close(); // Cierra la alerta de inactividad
            }
            try {
                // Cierra la ventana actual
                if (stage != null) {
                    stage.close();
                }
                // Carga la pantalla de inicio de sesión
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/passwordmanager/visuals/logInView.fxml"));
                Parent root = loader.load();
                Stage primaryStage = new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Método para actualizar el tiempo de inactividad configurado por el usuario
    public void setTiempoInactividad(long nuevoTiempo) {
        this.tiempoInactividad = nuevoTiempo;
        if (timer != null) {
            timer.cancel();
        }
        iniciarTimer(); // Reinicia el temporizador con el nuevo tiempo
    }
    public long getTiempoInactividad() {
        return tiempoInactividad;
    }

}
