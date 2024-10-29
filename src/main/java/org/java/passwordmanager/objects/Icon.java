package org.java.passwordmanager.objects;

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
}
