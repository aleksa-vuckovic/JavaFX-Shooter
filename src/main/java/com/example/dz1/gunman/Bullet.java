package com.example.dz1.gunman;

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

    public Bullet(float radius, int damage, Point2D position, Point2D direction, float speed, Paint fill) {
        this.radius = radius;
        this.damage = damage;
        this.position = new Translate(position.getX(), position.getY());
        this.direction = direction.normalize();
        this.speed = speed;

        circle = new Circle(radius);
        circle.setFill(fill);
        this.getChildren().addAll(circle);
        this.getTransforms().add(this.position);
    }
    public static Bullet regularBullet(Point2D position, Point2D direction) {
        return new Bullet(5f, 1, position, direction, 0.001f, Color.BLACK);
    }

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
