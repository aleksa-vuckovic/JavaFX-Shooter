package com.example.dz1;

import com.example.dz1.fields.Field;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Player extends Group {



    private Rotate rotate = new Rotate();
    private Translate position = new Translate();
    private float radius;

    public Player(float radius) {
        this.radius = radius;
        Polygon polygon = new Polygon(
            0.4f, 1f,
                1f, 0f,
                0.4f, -1f,
                -0.4f, -1f,
                -1f, 0f,
                -0.4f, 1f
        );
        polygon.setFill(Color.YELLOW);
        polygon.setStroke(Color.PURPLE); polygon.setStrokeWidth(0.05f);
        polygon.setScaleX(radius);
        polygon.setScaleY(radius);

        float gunWidth = radius/5;
        float gunLength = radius*2;
        Rectangle gun = new Rectangle(gunLength, gunWidth);
        gun.setTranslateX(-gunWidth/2);
        gun.setTranslateY(-gunWidth/2);
        gun.setFill(Color.PURPLE);

        this.getChildren().addAll(polygon, gun);
        this.getTransforms().addAll(position, rotate);

    }

    public Point2D center() {
        return this.getLocalToParentTransform().transform(0f, 0f);
    }
    public float getRadius() {
        return this.radius;
    }

    public void adjustRotate(Point2D target) {
        Point2D center = this.center();
        Point2D direction = target.subtract(center);
        double angle = direction.angle(1,0);
        this.rotate.setAngle(angle);
    }

    public void move(Point2D amount, Field field) {
        if (field.isInside(this)) {
            this.position.setX(this.position.getX() + amount.getX());
            this.position.setY(this.position.getY() + amount.getY());
        }
    }
}
