package com.example.dz1.gunman.body;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class SquareBody extends Body {

    public SquareBody() {
        float half = (float)(1/Math.sqrt(2));
        shape = new Rectangle(-half, -half, 2*half, 2*half);
        getChildren().addAll(shape);
    }

    @Override
    public boolean intersectsCircle(Point2D center, float radius) {
        return shape.getBoundsInLocal().intersects(center.getX() - radius, center.getY() - radius, radius*2, radius*2);
    }
}
