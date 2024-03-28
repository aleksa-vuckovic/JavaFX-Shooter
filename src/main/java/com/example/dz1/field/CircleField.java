package com.example.dz1.field;

import com.example.dz1.Utils;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Gunman;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;

public class CircleField extends Field {

    private double radius;
    private double enemyRadius;
    private double barrierRadius;
    public CircleField() {
        shape = new Circle();
        this.getChildren().addAll(shape);
    }

    @Override
    public void setDimensions(double width, double height) {
        super.setDimensions(width, height);
        double size = Math.min(width, height);
        this.radius = size*0.3;
        this.enemyRadius = size*0.45;
        this.barrierRadius = size*0.38;
        ((Circle)shape).setRadius(radius);
    }

    @Override
    public Point2D getEnemyPosition(int i) {
        int count = getEnemyCount();
        double angle = (double)i/count*360;
        return new Rotate(angle).transform(enemyRadius, 0);
    }

    @Override
    public Point2D getRandomPlatformPoint() {
        double a = Math.random()*360;
        double r = Math.random()*radius;
        return new Rotate(a).transform(r, 0);
    }

    @Override
    public Point2D getBarrierPosition(int i) {
        int count = getEnemyCount();
        double angle = (double)i/count*360;
        return new Rotate(angle).transform(barrierRadius, 0);
    }

    @Override
    public double getBarrierAngle(int i) {
        return (double) i / getEnemyCount() * 360;
    }
}
