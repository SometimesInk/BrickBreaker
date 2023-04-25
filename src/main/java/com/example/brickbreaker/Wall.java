package com.example.brickbreaker;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Wall {
    private int x;
    private int y;
    private final int width;
    private final int height;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void destroy(Pane root, Rectangle wall) {
        if(wall != null && root != null) {
            root.getChildren().remove(wall);
            x = -100;
            y = -100;
        }
    }
}
