package com.example.dz1.gunman.body;

import com.example.dz1.Utils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class HexagonBody extends Body {

    public HexagonBody() {
        shape = new Polygon(
                0.4f, -1f,
                1f, 0f,
                0.4f, 1f,
                -0.4f, 1f,
                -1f, 0f,
                -0.4f, -1f
        );
        this.getChildren().addAll(shape);
    }

    public boolean intersectsCircle(Point2D center, float radius) {
        return center.distance(Point2D.ZERO) <= radius + 1f;
    }
}
