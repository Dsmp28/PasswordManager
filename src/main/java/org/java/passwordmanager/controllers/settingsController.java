package org.java.passwordmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.passwordmanager.notifications.NotificationService;
import org.java.passwordmanager.notifications.Notifier;
import org.java.passwordmanager.objectControllers.RegistroController;
import org.java.passwordmanager.objects.Registro;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.ResourceBundle;

public class settingsController implements Initializable {
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtInactividad;
    @FXML
    private TextField txtPortapapeles;
    @FXML
    private ComboBox<String> cbExpiracion;
    @FXML
    private PasswordField txtAntigua;
    @FXML
    private PasswordField txtNueva;

    private final Notifier notifier = new NotificationService();
    private Stage stage;
    private PortapapelesController portapapelesController;

    private RegistroController registroController;

    public void setPortapapelesController(PortapapelesController portapapelesController) {
        this.portapapelesController = portapapelesController;
    }

    private InactividadController inactividadController;

    public void setInactividadController(InactividadController inactividadController) {
        this.inactividadController = inactividadController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRegistroController(RegistroController registroController) {
        this.registroController = registroController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarComboBox();
        txtInactividad.setText("60");
        txtPortapapeles.setText("30");
    }

    private void cargarComboBox() {
        cbExpiracion.getItems().add("7 días");
        cbExpiracion.getItems().add("15 días");
        cbExpiracion.getItems().add("21 días");
        cbExpiracion.getItems().add("30 días");
        cbExpiracion.getItems().add("45 días");
        cbExpiracion.getItems().add("60 días");
    }

    @FXML
    private void guardarCambios() {
        try {
            archivosController archivosController = new archivosController();
            String oldPassword = txtAntigua.getText();
            String newPassword = txtNueva.getText();

            if (!oldPassword.isEmpty() && !newPassword.isEmpty() && oldPassword.equals(archivosController.getDesPassword())) {
                archivosController.updateDesPassword(newPassword);
            } else if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
                notifier.showError("La contraseña antigua es incorrecta.");
                return;
            }

            // Actualizar tiempo de inactividad
            int nuevoTiempoInactividad = Integer.parseInt(txtInactividad.getText()) * 1000; // Convertir a milisegundos
            if (nuevoTiempoInactividad > 0 && inactividadController != null) {
                inactividadController.setTiempoInactividad(nuevoTiempoInactividad);
            }

            // Actualizar tiempo de limpieza del portapapeles
            int nuevoTiempoPortapapeles = Integer.parseInt(txtPortapapeles.getText()) * 1000; // Convertir a milisegundos
            if (nuevoTiempoPortapapeles > 0 && portapapelesController != null) {
                portapapelesController.setTiempoLimpiarPortapapeles(nuevoTiempoPortapapeles);
            }

            // Actualizar fecha de expiración en los registros
            actualizarFechaExpiracion();

            stage.close(); // Cierra la ventana de configuración
        } catch (NumberFormatException e) {
            notifier.showError("Los tiempos deben ser números enteros.");
            txtInactividad.setText("60"); // Restablecer a 60 si hay error
            txtPortapapeles.setText("30"); // Restablecer a 30 si hay error
        }
    }

    private void actualizarFechaExpiracion() {
        String seleccionExpiracion = cbExpiracion.getValue();

        if (seleccionExpiracion != null) {
            long dias = 30; // Default is 30 days
            switch (seleccionExpiracion) {
                case "7 días":
                    dias = 7;
                    break;
                case "15 días":
                    dias = 15;
                    break;
                case "21 días":
                    dias = 21;
                    break;
                case "30 días":
                    dias = 30;
                    break;
                case "45 días":
                    dias = 45;
                    break;
                case "60 días":
                    dias = 60;
                    break;
            }

            // Obtener la fecha de expiración basada en updateDate + días seleccionados
            if (registroController != null) {
                Map<Integer, Registro> registrosMap = registroController.getRegistros();
                if (registrosMap != null) {
                    for (Map.Entry<Integer, Registro> entry : registrosMap.entrySet()) {
                        Registro registro = entry.getValue();
                        LocalDateTime updateDate = registro.getUpdateDate(); // Obtener la updateDate
                        // Sumar los días seleccionados a la updateDate
                        LocalDateTime nuevaFechaExpiracion = updateDate.plus(dias, ChronoUnit.DAYS);
                        // Actualizar la fecha de expiración del registro
                        registro.setExpirationDate(nuevaFechaExpiracion);
                    }
                    // Guardar cambios después de la actualización
                    archivosController archivosController = new archivosController();
                    archivosController.guardarRegistros(registrosMap);
                }
            }
        }
    }

    @FXML
    private void salir() {
        //Cerrar ventana
        stage.close();
    }
}
