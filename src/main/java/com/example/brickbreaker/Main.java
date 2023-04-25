package com.example.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    Wall[] walls;
    Rectangle[] wallRectangles;
    Ball ball;
    Rectangle ballRectangle;
    Paddle paddle;
    Rectangle paddleRectangle;
    boolean gameOver;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //create the window
        primaryStage.setTitle("Brick Breaker");
        Pane root = new Pane();
        primaryStage.setScene(new Scene(root, 310, 250));
        root.setStyle("-fx-background-color: black;");
        primaryStage.setResizable(false);
        primaryStage.show();

        initializeComponents(root);

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
                                        walls[i].destroy(root, wallRectangles[i]);
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
                    gameOver = true;
                    ball.stop();
                    //show the game over screen
                    Text gameOver = new Text("GAME OVER");
                    gameOver.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                    gameOver.setFill(javafx.scene.paint.Color.WHITE);
                    gameOver.setX(50);
                    gameOver.setY(125);
                    root.getChildren().add(gameOver);
                    //show the restart text under the game over screen
                    Text restart = new Text("Press any button to restart");
                    restart.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    restart.setFill(javafx.scene.paint.Color.WHITE);
                    restart.setX(50);
                    restart.setY(175);
                    root.getChildren().add(restart);
                }

                //update the ball's position
                ball.move();

                //update the cube's position
                ballRectangle.setX(ball.getX());
                ballRectangle.setY(ball.getY());

                //get the key pressed and move the paddle accordingly
                root.requestFocus();
                root.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.LEFT) {
                        paddle.moveLeft();
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        paddle.moveRight();
                    }

                    if(gameOver)
                    {
                        root.getChildren().clear();
                        initializeComponents(root);
                    }
                });
                if(paddle.getX() <= 0) {
                    paddle.setX(0);
                }
                if(paddle.getX() >= 310 - paddle.getWidth()) {
                    paddle.setX(310 - paddle.getWidth());
                }

                //update the paddle's position
                paddleRectangle.setX(paddle.getX());
                paddleRectangle.setY(paddle.getY());
            }
        };
        gameLoop.start();
    }

    public void initializeComponents(Pane root)
    {
        //makes the game not over
        gameOver = false;

        //create the ball
        ball = new Ball(150, 125, 10, 1, 1);

        //draws a cube for the retro look
        ballRectangle = new Rectangle(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
        ballRectangle.setFill(javafx.scene.paint.Color.WHITE);
        root.getChildren().add(ballRectangle);

        walls = new Wall[] {
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

        wallRectangles = new Rectangle[walls.length];

        for (int i = 0; i < walls.length; i++) {
            wallRectangles[i] = new Rectangle(walls[i].getX(), walls[i].getY(), walls[i].getWidth(), walls[i].getHeight());
            wallRectangles[i].setFill(javafx.scene.paint.Color.WHITE);
            root.getChildren().add(wallRectangles[i]);
        }

        //create the paddle
        paddle = new Paddle(150, 200, 50, 10, 5);

        //draw the paddle
        paddleRectangle = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        paddleRectangle.setFill(javafx.scene.paint.Color.WHITE);
        root.getChildren().add(paddleRectangle);

        //randomize the starting direction of the ball
        ball.randomizeDirection();
    }
}
