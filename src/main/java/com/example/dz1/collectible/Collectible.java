package com.example.dz1.collectible;

import com.example.dz1.gunman.Player;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.util.Duration;

public abstract class Collectible extends Group {

    public Collectible() {}
    public final void setPosition(Point2D position) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
    }
    public void collect(Player player, Runnable onDisappear) {
        //The first part should be implemented by the subclasses.
        KeyValue kv1 = new KeyValue(scaleXProperty(), 0.3, Interpolator.EASE_BOTH);
        KeyValue kv2 = new KeyValue(scaleYProperty(), 0.3, Interpolator.EASE_BOTH);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(0.3), kv1, kv2);
        kv1 = new KeyValue(scaleXProperty(), 0.8, Interpolator.EASE_BOTH);
        kv2 = new KeyValue(scaleYProperty(), 0.8, Interpolator.EASE_BOTH);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.6), kv1, kv2);
        kv1 = new KeyValue(scaleXProperty(), 0, Interpolator.EASE_BOTH);
        kv2 = new KeyValue(scaleYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf3 = new KeyFrame(Duration.seconds(0.9), kv1, kv2);
        Timeline timeline = new Timeline(kf1, kf2, kf3);
        timeline.setOnFinished(actionEvent -> onDisappear.run());
        timeline.play();
    }

    public final Point2D getPosition() {
        return new Point2D(getTranslateX(), getTranslateY());
    }
}
