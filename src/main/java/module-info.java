module org.java.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.java.passwordmanager to javafx.fxml;
    exports org.java.passwordmanager;

    opens org.java.passwordmanager.controllers to javafx.fxml;
    exports org.java.passwordmanager.controllers;
}