package com.example.dz1;

import com.example.dz1.collectible.CoinCollectible;
import com.example.dz1.collectible.HeartCollectible;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Player;
import com.example.dz1.indicators.TimeIndicator;
import com.example.dz1.ui.Button;
import com.example.dz1.ui.Selection;
import com.example.dz1.ui.TextBox;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

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
            startScene.setRoot(new StartScreen());
            stage.setScene(startScene);
            game.clear();
        });

        stage.setScene(startScene);
        stage.setTitle("Shooter!");
        stage.show();
    }

/*
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        HeartCollectible coin = new HeartCollectible();
        coin.setPosition(new Point2D(100, 100));
        root.getChildren().addAll(coin);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }
*/
    public static void main(String[] args) {
        launch();
    }
}