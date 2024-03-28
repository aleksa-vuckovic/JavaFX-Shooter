package com.example.dz1.field;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HorizontalField extends Field {

    private double platformHeight;
    private double enemyDistance;
    private double barrierDistance;
    public HorizontalField() {
        shape = new Rectangle();
        this.getChildren().addAll(shape);
    }

    @Override
    public void setDimensions(double width, double height) {
        super.setDimensions(width, height);
        this.platformHeight = height*0.5;
        this.enemyDistance = height*0.4;
        this.barrierDistance = height*0.33;
        Rectangle rect = (Rectangle)shape;
        rect.setX(-width/2);
        rect.setY(-platformHeight/2);
        rect.setWidth(width);
        rect.setHeight(platformHeight);
    }

    @Override
    public Point2D getEnemyPosition(int i) {
        int count = getEnemyCount();
        boolean lower = i >= count/2;
        if (lower) {
            i = i - count/2;
            count = count - count/2;
        } else count = count/2;
        double spacing = getWidth()/(count+1);
        Point2D pos = new Point2D(-getWidth()/2 + spacing*(i+1), (lower ? 1 : -1) * enemyDistance);
        return pos;
    }

    @Override
    public Point2D getRandomPlatformPoint() {
        double x = Math.random()*getWidth();
        x -= getWidth()/2;
        double y = Math.random()*platformHeight;
        y -= platformHeight/2;
        return new Point2D(x, y);
    }

    @Override
    public Point2D getBarrierPosition(int i) {
        Point2D position = getEnemyPosition(i);
        return new Point2D(position.getX(), ((position.getY() < 0) ? -1 : 1)*barrierDistance);
    }

    @Override
    public double getBarrierAngle(int i) {
        return 90;
    }


}
