package com.example.dz1.fields;

import com.example.dz1.Player;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

public class CircleField extends Field {

    Circle circle;
    public CircleField(float radius) {
        circle = new Circle(radius);
        this.getChildren().addAll(circle);
    }

    @Override
    public boolean isInside(Player player) {
        Point2D playerCenter = player.center();
        Point2D fieldCenter = this.getLocalToParentTransform().transform(circle.getCenterX(), circle.getCenterY());
        return fieldCenter.distance(playerCenter) < circle.getRadius() - player.getRadius();
    }

    @Override
    public void setFill(Paint paint) {
        circle.setFill(paint);
    }
}
