package com.example.dz1.gunman;

import com.example.dz1.Game;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.gun.Gun;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 * This class represents an actor with a gun, with a specified size,
 * who can move and be position in and around the field,
 * who can have his gun rotated towards a specified point.
 */
public abstract class Gunman extends Group {
    protected Rotate rotate = new Rotate();
    protected Translate position = new Translate();
    protected Body body;
    protected Gun gun;
    protected Game game;
    protected float speed;
    private Timeline triggerTimeline;
    private Scale scale;

    Gunman(Body body, Gun gun) {
        this.body = body;
        this.gun = gun;
        this.speed = 10;
        this.scale = new Scale(1,1);

        Translate triggerTranslate = new Translate();

        KeyValue kv1 = new KeyValue(triggerTranslate.xProperty(), -0.4, Interpolator.LINEAR);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(0.1), kv1);
        KeyValue kv2 = new KeyValue(triggerTranslate.xProperty(), 0, Interpolator.LINEAR);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.2), kv2);
        triggerTimeline = new Timeline(kf1, kf2);

        this.getChildren().addAll(body, gun);
        this.getTransforms().addAll(position, rotate, scale, triggerTranslate);
    }
    public void setRadius(float radius) {
        scale.setX(radius);
        scale.setY(radius);
    }

    protected void trigger() {
        triggerTimeline.play();
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
        return (float) scale.getX();
    }

    /**
     * Adjust the rotation of the gunmen so that the gun points to the specified target.
     * @param target The point in game coordinates towards which the gun should be pointing.
     */
    public void adjustRotate(Point2D target) {
        Point2D direction = target.subtract(this.getCenter());
        if (direction.equals(Point2D.ZERO)) return;
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
     * Move the gunman to the specified position.
     * @param position The position in game coordinates.
     */
    public void setPosition(Point2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }


    protected final void fireBullet(Bullet bullet) {
        bullet.setDirection(rotate.transform(1, 0));
        bullet.setPosition(localToParent(this.gun.getTop()));
        bullet.setOwner(this);
        game.addBullet(bullet);
    }
    public abstract void fire();

    public abstract void take(Bullet bullet, Runnable onRemoveGunman);
    public final boolean interacts(Point2D point) {return body.contains(parentToLocal(point));}
    public final boolean interacts(Bullet bullet) {
        if (bullet.getOwner() == this) return false;
        Point2D bulletPosition = parentToLocal(bullet.getPosition());
        float bulletRadius = bullet.getRadius() / getRadius();
        return body.intersectsCircle(bulletPosition, bulletRadius);
    }

    public abstract void finish();
    public void start(Game game) {
        this.game = game;
    }
}
