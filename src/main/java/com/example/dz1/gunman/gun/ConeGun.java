package com.example.dz1.gunman.gun;

import com.example.dz1.gunman.gun.Gun;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class ConeGun extends Gun {

    private static final float WIDTH = 0.2f;
    private static final float LENGTH = 1.6f;
    private static final float CONE_LENGTH = 3*WIDTH;
    private static final float CONE_WIDTH = 3*WIDTH;

    public ConeGun() {
        shape = new Polygon(
                -WIDTH/2, WIDTH/2,
                -WIDTH/2, -WIDTH/2,
                LENGTH, -WIDTH/2,
                LENGTH + CONE_LENGTH, -CONE_WIDTH/2,
                LENGTH + CONE_LENGTH, CONE_WIDTH/2,
                LENGTH, WIDTH/2
        );
        this.getChildren().addAll(shape);
    }
    @Override
    public Point2D getTop() {
        return new Point2D(LENGTH + CONE_LENGTH, 0);
    }
}
