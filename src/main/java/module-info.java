module org.java.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires io.github.cdimascio.dotenv.java;
    requires org.json;
    requires org.bouncycastle.provider;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.java.passwordmanager to javafx.fxml;
    exports org.java.passwordmanager;

    opens org.java.passwordmanager.controllers to javafx.fxml;
    exports org.java.passwordmanager.controllers;

    exports org.java.passwordmanager.objects;

    exports org.java.passwordmanager.deserializers to com.fasterxml.jackson.databind;
}