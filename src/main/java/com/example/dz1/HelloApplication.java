package com.example.dz1;

import com.example.dz1.fields.CircleField;
import com.example.dz1.fields.Field;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final float WINDOW_WIDTH = 600f;
    private static final float WINDOW_HEIGHT = 600f;
    private static final float PLAYER_RADIUS = 20f;
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        Field field = new CircleField(WINDOW_WIDTH/3);
        field.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));
        Image grassImage = new Image("grass.jpg");
        Image waterImage = new Image("water.jpg");
        ImagePattern grassPattern = new ImagePattern(grassImage);
        ImagePattern waterPattern = new ImagePattern(waterImage);
        field.setFill(grassPattern);

        Player player = new Player(PLAYER_RADIUS);
        player.getTransforms().addAll(new Translate(WINDOW_WIDTH/2, WINDOW_HEIGHT/2));

        root.getChildren().addAll(field, player);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(waterPattern);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}