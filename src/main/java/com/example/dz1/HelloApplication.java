package com.example.dz1;

import com.example.dz1.ui.Button;
import com.example.dz1.ui.Selection;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    private static final float WINDOW_WIDTH = 600f;
    private static final float WINDOW_HEIGHT = 600f;
    private static final float PLAYER_RADIUS = 20f;
    private static final int ENEMY_COUNT = 4;

/*
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
        Player player = new Player(playerBody, playerGun, PLAYER_RADIUS, 4, 4, Utils.SPEED_MEDIUM, game);
        game.setPlayer(player);

        TimeIndicator timeIndicator = new TimeIndicator();
        timeIndicator.setPosition(new Point2D(-WINDOW_WIDTH/2, -WINDOW_HEIGHT/2));
        timeIndicator.start();
        game.setTimeIndicator(timeIndicator);

        for (int i = 0; i < ENEMY_COUNT; i++) {
            double angle = (double)i/ENEMY_COUNT*360;
            Point2D position = new Rotate(angle).transform(WINDOW_WIDTH*5/12, 0);
            Enemy enemy = Enemy.regularEnemy(game);
            enemy.setPosition(position);
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
*/

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        Rectangle option1 = new Rectangle(50,50, Color.BLUE);
        Circle option2 = new Circle(30, Color.GREEN);
        ArrayList<Node> options = new ArrayList<>();
        options.add(option1); options.add(option2);
        Selection<Node> selection = new Selection<>(options, 100, "Select this:");
        root.getChildren().addAll(selection);

        Button button = new Button("Ok", () -> {});
        button.setTranslateY(250);
        button.setTranslateX(50);
        root.getChildren().addAll(button);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}