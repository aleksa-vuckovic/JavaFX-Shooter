package com.example.dz1;

import com.example.dz1.field.CircleField;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Player;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.HexagonBody;
import com.example.dz1.gunman.gun.Gun;
import com.example.dz1.gunman.gun.RegularGun;
import com.example.dz1.indicators.BulletIndicator;
import com.example.dz1.indicators.TimeIndicator;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        Player player = new Player(playerBody, playerGun, PLAYER_RADIUS, 4, 4, game);
        game.setPlayer(player);

        TimeIndicator timeIndicator = new TimeIndicator();
        timeIndicator.setPosition(new Point2D(-WINDOW_WIDTH/2, -WINDOW_HEIGHT/2));
        timeIndicator.start();
        game.setTimeIndicator(timeIndicator);

        for (int i = 0; i < ENEMY_COUNT; i++) {
            double angle = (double)i/ENEMY_COUNT*360;
            Point2D position = new Rotate(angle).transform(WINDOW_WIDTH*5/12, 0);
            Enemy enemy = Enemy.regularEnemy(game);
            enemy.moveTo(position);
            game.addEnemy(enemy);
        }

        game.getTransforms().add(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                game.timeUpdate(interval);
            }
        }.start();

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

/*
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Rectangle rect = new Rectangle(100, 200);
        rect.setFill(Color.GREEN);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStroke(Color.BLACK);
        //rect.setTranslateY(300);
        //rect.setTranslateX(300);
        root.getChildren().add(rect);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(10), rect);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(0);
        //scaleTransition.play();
        System.out.println(rect.getLocalToParentTransform());
        //rect.setScaleX(0.5);
        rect.getTransforms().addAll(new Scale(0.5, 1));
        System.out.println(rect.getBoundsInLocal());
        System.out.println(rect.getLocalToParentTransform());

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.YELLOW);
        stage.setScene(scene);
        stage.show();
    }
*/
    public static void main(String[] args) {
        launch();
    }
}