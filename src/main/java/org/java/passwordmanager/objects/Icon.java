package org.java.passwordmanager.objects;

import javafx.scene.image.Image;

public class Icon {
    private String name;
    private int height;
    private int width;
    private String path;

    public Icon(String name, int height, int width, String path) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.path = path;
    }

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

    //Setters
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

    public String getImagen() {
        return path;
    }

    public Image getImage() {
        return new Image(path);
    }
}
