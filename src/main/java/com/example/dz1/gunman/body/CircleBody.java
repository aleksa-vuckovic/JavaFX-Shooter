package com.example.dz1.gunman.body;

import com.example.dz1.Utils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.shape.Circle;

public class CircleBody extends Body {
    public CircleBody() {
        shape = new Circle(1f);
        this.getChildren().addAll(shape);
    }

    public boolean intersectsCircle(Point2D center, float radius) {
        return center.distance(Point2D.ZERO) <= radius + 1f;
    }
}
