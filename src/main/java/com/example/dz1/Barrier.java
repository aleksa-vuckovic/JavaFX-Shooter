package com.example.dz1;

import com.example.dz1.gunman.Bullet;
import com.example.dz1.gunman.Player;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Barrier extends Group {

    public static final double WIDTH = 7.5;
    public static final double HEIGHT = 75;

    private Rectangle rect;
    private double time;
    private IntervalTimer timer;
    private Runnable onRemove;
    public Barrier(double duration) {
        rect = new Rectangle(-WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);
        rect.setArcWidth(WIDTH/2);
        rect.setArcHeight(WIDTH/2);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.LIGHTBLUE);
        rect.setStrokeWidth(WIDTH/4);
        getChildren().addAll(rect);

        time = 0;
        timer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                time += interval;
                if (time > duration) {
                    timer.stop();
                    timer = null;
                    onRemove.run();
                }
            }
        };
    }

    public void setPosition(Point2D position) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
    }

    public void setAngle(double angle) {
        setRotate(angle);
    }

    public boolean interacts(Bullet bullet) {
        if (!(bullet.getOwner() instanceof Player)) return false;
        return bullet.getBoundsInParent().intersects(getBoundsInParent());
    }
    public void setOnRemove(Runnable onRemove) { this.onRemove = onRemove; }
    public void start() {timer.start();}
}
