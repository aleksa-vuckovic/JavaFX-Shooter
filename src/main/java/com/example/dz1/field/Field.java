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
    public abstract boolean isWithinBounds(Gunman gunman);
    public abstract void setFill(Paint paint);

}

