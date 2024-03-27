package com.example.dz1.field;

import com.example.dz1.gunman.Gunman;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Field extends Group {

    protected Shape shape;
    private Rectangle back;
    private double width, height;
    private int enemyCount;
    public Field() {
        back = new Rectangle();
        getChildren().addAll(back);
    }
    public void setDimensions(double width, double height) {
        this.width = width; this.height = height;
        back.setX(-width/2);
        back.setY(-height/2);
        back.setWidth(width);
        back.setHeight(height);
    }
    public static Field islandField() {
        Field field = new CircleField();
        Image waterImage = new Image("water.jpg");
        ImagePattern waterPattern = new ImagePattern(waterImage);
        field.setBackFill(waterPattern);
        Image grassImage = new Image("grass.jpg");
        ImagePattern grassPattern = new ImagePattern(grassImage);
        field.setPlatformFill(grassPattern);
        field.setEnemyCount(4);
        return field;
    }
    public static Field bridgeField() {
        Field field = new HorizontalField();
        Image cloudImage = new Image("clouds.jpg");
        ImagePattern cloudPattern = new ImagePattern(cloudImage);
        field.setBackFill(cloudPattern);
        Image woodImage = new Image("wood.jpg");
        ImagePattern woodPattern = new ImagePattern(woodImage);
        field.setPlatformFill(woodPattern);
        field.setEnemyCount(7);
        return field;
    }
    public static Field starField() {
        Field field = new StarField();
        Image nightImage = new Image("night.jpg");
        ImagePattern nightPattern = new ImagePattern(nightImage);
        field.setBackFill(nightPattern);
        Image metalImage = new Image("metal.jpg");
        ImagePattern metalPattern = new ImagePattern(metalImage);
        field.setPlatformFill(metalPattern);
        field.setEnemyCount(7);
        return field;
    }

    public final void setBackFill(Paint fill) { back.setFill(fill); }
    /**
     * Checks whether the given point is within the bounds of this field.
     * @param point The point in game coordinates.
     */
    public final boolean isInside(Point2D point) { return shape.contains(point); }
    public final boolean isInside(Bounds bounds) { return isInside(new Point2D(bounds.getCenterX(), bounds.getCenterY()));}
    public final boolean isInside(Gunman gunman) {
        return isInside(gunman.getCenter());
    }
    public void setBackgroundFill(Paint paint) { this.back.setFill(paint); }
    public void setPlatformFill(Paint paint) { shape.setFill(paint); }

    public void setEnemyCount(int count) { this.enemyCount = count; }
    public int getEnemyCount() { return enemyCount; }
    public abstract Point2D getEnemyPosition(int i);
    public double getWidth() {return width;}
}

