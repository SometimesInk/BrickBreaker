package com.example.brickbreaker;

public class Ball {
    private int x;
    private int y;
    private int radius;
    private int xSpeed;
    private int ySpeed;

    public Ball(int x, int y, int radius, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public void bounceX() {
        xSpeed *= -1;
    }

    public void bounceY() {
        ySpeed *= -1;
    }

    public void invertXSpeed() {
        xSpeed *= -1;
    }

    public void invertYSpeed() {
        ySpeed *= -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void stop() {
        xSpeed = 0;
        ySpeed = 0;
    }

    public void randomizeDirection() {
        boolean randomX = Math.random() < 0.5;
        boolean randomY = Math.random() < 0.5;
        if(randomX)
            xSpeed = 1;
        else
            xSpeed = -1;
        if(randomY)
            ySpeed = 1;
        else
            ySpeed = -1;
    }
}
