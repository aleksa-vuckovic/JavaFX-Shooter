package com.example.dz1.gunman.body;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Represents the body of a gunman.
 */
public abstract class Body extends Group {

    protected Shape shape;

    /**
     * @param point A point given in the local coordinate system.
     * @return True if the point is inside the body (or on the edge).
     */
    public boolean contains(Point2D point) {
        return shape.contains(point);
    }


    public void setFill(Paint paint) {
        shape.setFill(paint);
    }
    public void setStroke(Paint paint) {
        shape.setStroke(paint);
        shape.setStrokeWidth(0.01f);
    }

}