package com.example.dz1.field;

import com.example.dz1.Utils;
import com.example.dz1.gunman.Gunman;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.NonInvertibleTransformException;

public class CircleField extends Field {

    Circle circle;
    public CircleField(float radius) {
        circle = new Circle(radius);
        this.getChildren().addAll(circle);
    }

    @Override
    public boolean isWithinBounds(Gunman gunman) {
        Bounds gunmanBounds = gunman.getBodyBounds();
        try {
            gunmanBounds = this.getLocalToParentTransform().inverseTransform(gunmanBounds);
        } catch(NonInvertibleTransformException ignored) {}

        for (Point2D corner: Utils.getCorners(gunmanBounds))
            if (!circle.contains(corner)) return false;
        return true;
    }

    @Override
    public void setFill(Paint paint) {
        circle.setFill(paint);
    }
}
