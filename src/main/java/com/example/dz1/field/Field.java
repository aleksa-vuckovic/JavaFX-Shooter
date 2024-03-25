package com.example.dz1.field;

import com.example.dz1.gunman.Gunman;
import com.example.dz1.gunman.Player;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Translate;

public abstract class Field extends Group {

    protected Translate position = new Translate();
    public Field() {
        this.getTransforms().addAll(position);
    }
    public void setPosition(float x, float y) {
        position.setX(x);
        position.setY(y);
    }
    public Point2D center() {
        Bounds bounds = this.getBoundsInParent();
        return new Point2D(bounds.getCenterX(), bounds.getCenterY());
    }
    /**
     * Checks whether the given point is within the bounds of this field.
     * @param point The point in game coordinates.
     */
    public abstract boolean isWithinBounds(Point2D point);
    public final boolean isWithinBounds(Bounds bounds) { return isWithinBounds(new Point2D(bounds.getCenterX(), bounds.getCenterY()));}
    public final boolean isWithinBounds(Gunman gunman) {
        return isWithinBounds(gunman.getCenter());
    }

    public abstract void setFill(Paint paint);

}

