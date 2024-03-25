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
    public boolean isWithinBounds(Point2D point) {
        point = this.parentToLocal(point);
        return circle.contains(point);
    }

    @Override
    public void setFill(Paint paint) {
        circle.setFill(paint);
    }
}
