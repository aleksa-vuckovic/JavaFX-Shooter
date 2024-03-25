package com.example.dz1.gunman.body;

import com.example.dz1.Utils;
import com.example.dz1.gunman.Bullet;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Represents the body of a gunman.
 */
public abstract class Body extends Group {

    protected Shape shape;
    public abstract boolean intersectsCircle(Point2D center, float radius);


    public void setFill(Paint paint) {
        shape.setFill(paint);
    }
    public Paint getFill() {
        return shape.getFill();
    }
    public void setStroke(Paint paint) {
        shape.setStroke(paint);
        shape.setStrokeWidth(0.01f);
    }

}