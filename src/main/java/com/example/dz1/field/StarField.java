package com.example.dz1.field;

import com.example.dz1.Utils;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class StarField extends Field {
    private static final int POINTS = 5;

    private double innerRadius;
    private double outerRadius;
    private double enemyRadius;
    public StarField() {
        shape = new Polygon();
        this.getChildren().addAll(shape);
    }
    @Override
    public void setDimensions(double width, double height) {
        super.setDimensions(width, height);
        double size = Math.min(width, height);
        this.innerRadius = size*0.2;
        this.outerRadius = size*0.4;
        this.enemyRadius = size*0.4;
        Polygon star = (Polygon) shape;
        star.getPoints().clear();
        for (int i = 0; i < POINTS; i++) {
            Point2D inner = Utils.getCirclePoint(POINTS, i, innerRadius, 0);
            Point2D outer = Utils.getCirclePoint(POINTS, i, outerRadius, 0.5);
            star.getPoints().addAll(inner.getX(), inner.getY(), outer.getX(), outer.getY());
        }
    }

    @Override
    public Point2D getEnemyPosition(int i) {
        int count = getEnemyCount();
        double angle = (double)i/count*360;
        return new Rotate(angle).transform(enemyRadius, 0);
    }

    @Override
    public void setEnemyCount(int count) {}
    @Override
    public int getEnemyCount() {return POINTS;}

    @Override
    public Point2D getRandomPlatformPoint() {
        int point = (int)(Math.random()*POINTS);
        return Utils.getCirclePoint(POINTS, point, (innerRadius + outerRadius)/2, 0.5);
    }
}
