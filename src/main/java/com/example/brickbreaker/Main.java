package com.example.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //create the window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Brick Breaker");
        Pane root = new Pane();
        primaryStage.setScene(new Scene(root, 310, 250));
        root.setStyle("-fx-background-color: black;");
        primaryStage.setResizable(false);
        primaryStage.show();

        //create the ball
        Ball ball = new Ball(150, 125, 10, 1, 1);

        //draws a cube for the retro look
        Rectangle rect = new Rectangle(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
        rect.setFill(javafx.scene.paint.Color.WHITE);
        root.getChildren().add(rect);

        //initialize the wall positions
        Wall[] walls = {
            new Wall(10, 10, 40, 10),
            new Wall(60, 10, 40, 10),
            new Wall(110, 10, 40, 10),
            new Wall(160, 10, 40, 10),
            new Wall(210, 10, 40, 10),
            new Wall(260, 10, 40, 10),
            new Wall(10, 30, 40, 10),
            new Wall(60, 30, 40, 10),
            new Wall(110, 30, 40, 10),
            new Wall(160, 30, 40, 10),
            new Wall(210, 30, 40, 10),
            new Wall(260, 30, 40, 10),
            new Wall(10, 50, 40, 10),
            new Wall(60, 50, 40, 10),
            new Wall(110, 50, 40, 10),
            new Wall(160, 50, 40, 10),
            new Wall(210, 50, 40, 10),
            new Wall(260, 50, 40, 10),
            new Wall(10, 70, 40, 10),
            new Wall(60, 70, 40, 10),
            new Wall(110, 70, 40, 10),
            new Wall(160, 70, 40, 10),
            new Wall(210, 70, 40, 10),
            new Wall(260, 70, 40, 10),
        };

        Rectangle[] wallRects = new Rectangle[walls.length];

        for (int i = 0; i < walls.length; i++) {
            wallRects[i] = new Rectangle(walls[i].getX(), walls[i].getY(), walls[i].getWidth(), walls[i].getHeight());
            wallRects[i].setFill(javafx.scene.paint.Color.WHITE);
            root.getChildren().add(wallRects[i]);
        }

        //create the paddle
        Paddle paddle = new Paddle(150, 200, 50, 10, 5);

        //draw the paddle
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        paddleRect.setFill(javafx.scene.paint.Color.WHITE);
        root.getChildren().add(paddleRect);

        //randomize the starting direction of the ball
        ball.randomizeDirection();

        //game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //check for collisions with the paddle
                if (ball.getX() + ball.getRadius() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth()) {
                    if (ball.getY() + ball.getRadius() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight()) {
                        ball.bounceY();
                    }
                }

                //check for collisions with the walls
                for (Wall wall : walls) {
                    if (wall != null) {
                        if (ball.getX() + ball.getRadius() >= wall.getX() && ball.getX() <= wall.getX() + wall.getWidth()) {
                            if (ball.getY() + ball.getRadius() >= wall.getY() && ball.getY() <= wall.getY() + wall.getHeight()) {
                                ball.bounceY();

                                //find which wall was hit and remove it
                                for (int i = 0; i < walls.length; i++) {
                                    if (wall.getX() == walls[i].getX() && wall.getY() == walls[i].getY()) {
                                        walls[i].destroy(root, wallRects[i]);
                                    }
                                }
                            }
                        }
                    }
                }

                //check for collisions with the window borders (except the bottom)
                if (ball.getX() + ball.getRadius() >= root.getWidth() || ball.getX() <= 0) {
                    ball.bounceX();
                }
                if (ball.getY() <= 0) {
                    ball.bounceY();
                }

                //check for collisions with the bottom of the window
                if (ball.getY() + ball.getRadius() >= root.getHeight()) {
                    ball.stop();
                }

                //update the ball's position
                ball.move();

                //update the cube's position
                rect.setX(ball.getX());
                rect.setY(ball.getY());

                //get the key pressed and move the paddle accordingly
                root.requestFocus();
                root.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.LEFT) {
                            paddle.moveLeft();
                        } else if (event.getCode() == KeyCode.RIGHT) {
                            paddle.moveRight();
                        }
                    }
                });
                if(paddle.getX() <= 0) {
                    paddle.setX(0);
                }
                if(paddle.getX() >= 310) {
                    paddle.setX(310);
                }

                //update the paddle's position
                paddleRect.setX(paddle.getX());
                paddleRect.setY(paddle.getY());
            }
        };
        gameLoop.start();
    }
}
