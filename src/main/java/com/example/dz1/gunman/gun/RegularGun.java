package com.example.dz1.gunman.gun;

import com.example.dz1.gunman.gun.Gun;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class RegularGun extends Gun {
    private static final float WIDTH = 0.2f;
    private static final float LENGTH = 2;

    public RegularGun() {
        shape = new Rectangle(LENGTH, WIDTH);
        shape.setTranslateX(-WIDTH/2);
        shape.setTranslateY(-WIDTH/2);
        this.getChildren().addAll(shape);
    }


    @Override
    public Point2D getTop() {
        return new Point2D(LENGTH, 0);
    }
}
