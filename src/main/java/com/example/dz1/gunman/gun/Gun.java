package com.example.dz1.gunman.gun;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Represents a gunman's gun. Should be suited to a gunman diameter of 1.
 * The implementations are expected to set the shape field, and add it to
 * the group.
 */
public abstract class Gun extends Group {

    protected Shape shape;
    public abstract Point2D getTop();
    public void setFill(Paint paint) {
        shape.setFill(paint);
    }
    public void setStroke(Paint paint) {
        shape.setStroke(paint);
        shape.setStrokeWidth(0.01f);
    }
}
