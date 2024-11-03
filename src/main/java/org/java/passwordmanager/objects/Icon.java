package org.java.passwordmanager.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import javafx.scene.image.Image;

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

    @JsonIgnore
    public Image getImage() {
        return new Image(path);
    }

    // Método de fábrica para deserializar desde `String`
    @JsonCreator
    public static Icon fromPath(String path) {
        return new Icon("", 0, 0, path); // Inicializar `name`, `height` y `width` con valores predeterminados
    }
}

