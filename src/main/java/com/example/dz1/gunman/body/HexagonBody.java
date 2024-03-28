package com.example.dz1.gunman.body;

import com.example.dz1.Utils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class HexagonBody extends Body {

    public HexagonBody() {
        shape = Utils.getHexagon(1);
        this.getChildren().addAll(shape);
    }

    public boolean intersectsCircle(Point2D center, float radius) {
        return center.distance(Point2D.ZERO) <= radius + 1f;
    }
}
