package com.example.dz1.gunman;

import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.gun.Gun;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * This class represents an actor with a gun, with a specified size,
 * who can move and be position in and around the field,
 * who can have his gun rotated towards a specified point.
 */
public abstract class Gunman extends Group {
    protected Rotate rotate = new Rotate();
    protected Translate position = new Translate();
    protected final float radius;
    protected Body body;
    protected Gun gun;
    protected Game game;
    protected float speed;

    Gunman(Body body, Gun gun, float radius, Game game) {
        this.body = body;
        this.gun = gun;
        this.radius = radius;
        this.game = game;
        this.speed = 10;

        this.getChildren().addAll(body, gun);
        this.getTransforms().addAll(position, rotate, new Scale(radius, radius));
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Point2D getCenter() {
        return this.localToParent(0f, 0f);
    }
    public float getRadius() {
        return this.radius;
    }

    /**
     * Adjust the rotation of the gunmen so that the gun points to the specified target.
     * @param target The point in game coordinates towards which the gun should be pointing.
     */
    public void adjustRotate(Point2D target) {
        Point2D direction = target.subtract(this.getCenter());
        double angle = direction.angle(1,0);
        if (direction.getY() < 0) angle = -angle;
        this.rotate.setAngle(angle);
    }
    /**
     * Adjust the rotation of the gunmen so that the gun points to the specified target.
     * @param gunman The gunman towards which the gun should be pointing.
     */
    public void adjustRotate(Gunman gunman) {
        this.adjustRotate(gunman.getCenter());
    }

    /**
     * @return The body bounds in game coordinates.
     */
    public Bounds getBodyBounds() {
        return localToParent(body.getBoundsInLocal());
    }

    /**
     * Move the gunman by the amount specified.
     * Will only move if the field bounds allow.
     * @param amount The displacement vector.
     */
    public void moveBy(Point2D amount) {
        this.position.setX(this.position.getX() + amount.getX()*speed);
        this.position.setY(this.position.getY() + amount.getY()*speed);
        if (!game.getField().isWithinBounds(this)) {
            this.position.setX(this.position.getX() - amount.getX()*speed);
            this.position.setY(this.position.getY() - amount.getY()*speed);
        }
    }

    /**
     * Move the gunman to the specified position.
     * @param position The position in game coordinates.
     */
    public void moveTo(Point2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    /**
     * Fire a bullet.
     */
    public void fire() {
        Point2D direction = rotate.transform(1, 0);
        Point2D position = localToParent(this.gun.getTop());
        Bullet bullet = Bullet.regularBullet(position, direction);
        game.addBullet(bullet);
    }

    /**
     * @return True if an interaction occurred.
     */
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        Point2D bulletPosition = parentToLocal(bullet.getPosition());
        float bulletRadius = bullet.getRadius() / radius;
        return body.intersectsCircle(bulletPosition, bulletRadius);
    }
}
