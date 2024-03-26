package com.example.dz1.indicators;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.security.Key;

public class LivesIndicator extends Group {

    private static final float PAD = 10f;
    private static final float WIDTH = 15f;
    private static final float HEIGHT = 20f;

    private int lives;
    private int maxLives;

    public LivesIndicator(int lives) {
        this.maxLives = lives;
        this.lives = 0;
        set(lives);
    }

    public void inc() {
        if (lives == maxLives) return;
        lives++;
        Rectangle rect = new Rectangle(WIDTH, HEIGHT);
        rect.setFill(Color.GREEN);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStroke(Color.BLACK);
        rect.setTranslateY(-PAD - HEIGHT);
        rect.setTranslateX(PAD + lives*WIDTH);
        getChildren().add(rect);

        Scale scale = new Scale(0,1);
        rect.getTransforms().add(scale);
        KeyValue kv = new KeyValue(scale.xProperty(), 1, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        Timeline timeline = new Timeline(kf);
        timeline.play();
    }
    public void dec() {
        if (lives == 0) return;
        Node rect = getChildren().get(--lives);

        Scale scale = new Scale(1,1);
        rect.getTransforms().add(scale);
        KeyValue kv = new KeyValue(scale.xProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(actionEvent -> {
            getChildren().remove(rect);
        });
        timeline.play();
    }
    public void set(int lives) {
        if (lives > maxLives) lives = maxLives;
        else if (lives < 0) lives = 0;
        while (lives < this.lives) dec();
        while (lives > this.lives) inc();
    }
    public int get() {
        return lives;
    }
    public boolean dead() {
        return lives == 0;
    }
    public void setPosition(Point2D position) {
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
    }
}
