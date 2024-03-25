package com.example.dz1;

import com.example.dz1.field.CircleField;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Game;
import com.example.dz1.gunman.Player;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.CircleBody;
import com.example.dz1.gunman.body.HexagonBody;
import com.example.dz1.gunman.gun.Gun;
import com.example.dz1.gunman.gun.RegularGun;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final float WINDOW_WIDTH = 600f;
    private static final float WINDOW_HEIGHT = 600f;
    private static final float PLAYER_RADIUS = 20f;
    private static final int ENEMY_COUNT = 4;
    @Override
    public void start(Stage stage) throws IOException {
        Game game = new Game(WINDOW_WIDTH, WINDOW_HEIGHT);

        Field field = new CircleField(WINDOW_WIDTH/3);
        Image grassImage = new Image("grass.jpg");
        ImagePattern grassPattern = new ImagePattern(grassImage);
        field.setFill(grassPattern);
        game.setField(field);

        Body playerBody = new HexagonBody();
        playerBody.setFill(Color.YELLOW);
        playerBody.setStroke(Color.PURPLE);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.PURPLE);
        Player player = new Player(playerBody, playerGun, PLAYER_RADIUS, game);
        game.setPlayer(player);

        for (int i = 0; i < ENEMY_COUNT; i++) {
            double angle = (double)i/ENEMY_COUNT*360;
            Point2D position = new Rotate(angle).transform(WINDOW_WIDTH*5/12, 0);
            Enemy enemy = Enemy.regularEnemy(game);
            enemy.moveTo(position);
            game.addEnemy(enemy);
        }

        game.getTransforms().add(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        AnimationTimer timer = new AnimationTimer() {
            long prev = 0;
            @Override
            public void handle(long now) {
                now /= 1000;
                if (prev != 0) game.timeUpdate(now - prev);
                prev = now;
            }
        };
        timer.start();

        Scene scene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.addEventHandler(KeyEvent.ANY, game::onKeyEvent);
        scene.addEventHandler(MouseEvent.ANY, game::onMouseEvent);
        Image waterImage = new Image("water.jpg");
        ImagePattern waterPattern = new ImagePattern(waterImage);
        scene.setFill(waterPattern);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}