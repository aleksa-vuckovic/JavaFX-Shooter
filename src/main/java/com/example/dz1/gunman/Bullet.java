package com.example.dz1.gunman;

import com.example.dz1.Utils;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

public class Bullet extends Group {

    private float radius;
    private int damage;
    private Circle circle;
    private Translate position;
    private Point2D direction;
    /**
     * Bullet speed in pixels per millisecond.
     */
    private float speed;

    public Bullet(float radius, int damage, float speed, Paint fill) {
        this.radius = radius;
        this.damage = damage;
        this.speed = speed;
        this.position = new Translate(-10000,-10000);
        this.direction = Point2D.ZERO;

        circle = new Circle(radius);
        circle.setFill(fill);
        this.getChildren().addAll(circle);
        this.getTransforms().add(this.position);
    }
    public void setPosition(Point2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }
    public void setDirection(Point2D direction) {
        this.direction = direction.normalize();
    }
    public static Bullet regularBullet() {
        return new Bullet(5f, 1, Utils.SPEED_BULLET, Color.BLACK);
    }
    public static Bullet slowBullet() {
        return new Bullet(5f, 1, Utils.SPEED_SLOW, Color.BLACK);
    }
    public static Bullet bigBullet() { return new Bullet(10f, 2, Utils.SPEED_SLOW, Color.RED);}

    public void timeUpdate(long interval) {
        Point2D move = direction.multiply(interval * speed);
        position.setX(position.getX() + move.getX());
        position.setY(position.getY() + move.getY());
    }

    public int getDamage() {
        return damage;
    }
    public Point2D getPosition() {
        return new Point2D(position.getX(), position.getY());
    }
    public float getRadius() {
        return radius;
    }
}
