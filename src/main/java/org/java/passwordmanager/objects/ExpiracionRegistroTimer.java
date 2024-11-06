package org.java.passwordmanager.objects;

import javafx.application.Platform;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objectControllers.RegistroController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ExpiracionRegistroTimer {
    private final Notifier notifier = new NotificationService();
    private Timer timer;

    // Usamos un conjunto para registrar los registros ya notificados
    private Set<Integer> notifiedRecords = new HashSet<>();

    // Variable estática que indica si la alerta ya fue mostrada
    private static boolean alertAlreadyShown = false;

    public ExpiracionRegistroTimer() {
        timer = new Timer();
    }

    public void startExpirationCheck() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkExpirationDates();
            }
        }, 0, 60000); // Ejecutar cada minuto
    }

    private void checkExpirationDates() {
        StringBuilder expirados = new StringBuilder();
        boolean expirado = false;

        // Recorremos los registros para verificar si han expirado
        for (Map.Entry<Integer, Registro> entry : RegistroController.registros.entrySet()) {
            Registro registro = entry.getValue();
            // Comprobar si la fecha de expiración es antes de la fecha actual
            if (registro.getExpirationDate() != null && registro.getExpirationDate().isBefore(LocalDateTime.now())) {
                expirado = true;
                // Verificar si ya se notificó este registro
                if (!notifiedRecords.contains(registro.getId())) {
                    // Agregar el registro a la lista de notificados
                    notifiedRecords.add(registro.getId());
                    // Agregar el nombre o detalles del registro expirado al mensaje
                    expirados.append("ID: ").append(registro.getId())
                            .append(" - Sitio: ").append(registro.getSiteName())
                            .append("\n");
                }
            }
        }

        // Si hay registros expirados y no se ha mostrado la alerta, mostramos la alerta
        if (expirado && !alertAlreadyShown && expirados.length() > 0) {
            // Marcar que la alerta ha sido mostrada para que no se repita
            alertAlreadyShown = true;

            String mensaje = expirados.toString();
            Platform.runLater(() -> {
                notifier.showWarning("Alerta de Expiración\n" + "Los siguientes registros han expirado:\n" + mensaje);
            });
        }
    }

    public void stopExpirationCheck() {
        if (timer != null) {
            timer.cancel();
        }
    }

    // Método para reiniciar el estado de la alerta cuando sea necesario
    public static void resetAlertState() {
        alertAlreadyShown = false;
    }
}
