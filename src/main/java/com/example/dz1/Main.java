package com.example.dz1;

import com.example.dz1.field.Field;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Player;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final float WINDOW_WIDTH = 600f;
    private static final float WINDOW_HEIGHT = 600f;


    @Override
    public void start(Stage stage) throws IOException {
        Game game = new Game(WINDOW_WIDTH, WINDOW_HEIGHT);
        game.getTransforms().add(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        StartScreen startScreen = new StartScreen();
        Scene startScene = new Scene(startScreen, WINDOW_WIDTH, WINDOW_HEIGHT);
        startScene.setFill(Color.BLACK);
        Scene gameScene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameScene.addEventHandler(KeyEvent.ANY, game::onKeyEvent);
        gameScene.addEventHandler(MouseEvent.ANY, game::onMouseEvent);

        startScreen.setOnStart(() -> {
            Player player = startScreen.getPlayer();
            Field field = startScreen.getField();
            String difficulty = startScreen.getDifficulty();
            field.setDimensions(WINDOW_WIDTH, WINDOW_HEIGHT);
            game.setField(field);
            game.setPlayer(player);

            int lives = 4;
            if (difficulty.equals("Medium")) lives = 8;
            else if (difficulty.equals("Hard")) lives = 15;
            for (int i = 0; i < field.getEnemyCount(); i++) {
                Enemy enemy = Enemy.regularEnemy(lives);
                enemy.setPosition(field.getEnemyPosition(i));
                game.addEnemy(enemy);
            }

            game.start();
            stage.setScene(gameScene);
        });
        game.setOnBack(() -> {
            game.clear();
            startScreen.reset();
            stage.setScene(startScene);

        });

        startScreen.reset();
        stage.setScene(startScene);
        stage.setTitle("Shooter!");
        stage.show();
    }

/*
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        Barrier barrier = new Barrier(5000);
        barrier.setOnRemove(() -> {root.getChildren().remove(barrier);});
        barrier.start();
        barrier.setPosition(new Point2D(100, 100));
        barrier.setAngle(60);
        root.getChildren().addAll(barrier);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }
*/
    public static void main(String[] args) {
        launch();
    }
}