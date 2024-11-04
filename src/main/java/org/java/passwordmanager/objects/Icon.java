package org.java.passwordmanager.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

public class Icon {
    private String name;
    private int height;
    private int width;
    private String path;

    // Constructor completo
    public Icon(String name, int height, int width, String path) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.path = path;
    }

    public Icon() {}

    // Getters
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Serializar `Icon` como `path`
    @JsonValue
    public String getImagen() {
        return path;
    }

    // Obtener la imagen de respaldo en caso de error
    @JsonIgnore
    public Image getImage() {
        try {
            File file = new File(path.replace("file:", ""));
            if (file.exists()) {
                return new Image(file.toURI().toString());
            } else {
                System.out.println("Imagen no encontrada en la ruta: " + path + ". Usando imagen de respaldo.");
                return getBackupImage();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("URL de imagen no válida. Usando imagen de respaldo.");
            return getBackupImage();
        }
    }

    private Image getBackupImage() {
        return new Image(Objects.requireNonNull(getClass().getResource("/org/java/passwordmanager/images/imagen.png")).toExternalForm());
    }

    // Método de fábrica para deserializar desde `String`
    @JsonCreator
    public static Icon fromPath(String path) {
        return new Icon("", 0, 0, path); // Inicializar `name`, `height` y `width` con valores predeterminados
    }
}


