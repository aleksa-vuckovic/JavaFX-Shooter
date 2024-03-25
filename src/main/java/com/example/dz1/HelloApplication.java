package com.example.dz1;

import com.example.dz1.field.CircleField;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.Game;
import com.example.dz1.gunman.Player;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.CircleBody;
import com.example.dz1.gunman.body.HexagonBody;
import com.example.dz1.gunman.gun.Gun;
import com.example.dz1.gunman.gun.RegularGun;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final float WINDOW_WIDTH = 600f;
    private static final float WINDOW_HEIGHT = 600f;
    private static final float PLAYER_RADIUS = 20f;
    @Override
    public void start(Stage stage) throws IOException {
        Game game = new Game();

        Field field = new CircleField(WINDOW_WIDTH/3);
        Image grassImage = new Image("grass.jpg");
        ImagePattern grassPattern = new ImagePattern(grassImage);
        field.setFill(grassPattern);

        Body playerBody = new HexagonBody();
        playerBody.setFill(Color.YELLOW);
        playerBody.setStroke(Color.PURPLE);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.PURPLE);
        Player player = new Player(playerBody, playerGun, PLAYER_RADIUS, game);

        game.setField(field);
        game.setPlayer(player);
        game.getTransforms().add(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        Scene scene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.addEventHandler(KeyEvent.ANY, game::onKeyEvent);
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