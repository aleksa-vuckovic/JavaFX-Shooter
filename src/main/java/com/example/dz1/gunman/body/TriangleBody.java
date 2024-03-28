package com.example.dz1.gunman.body;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleBody extends Body {

    private Shape getEquiTri(float scale) {
        float t = (float) Math.sqrt(3);
        return new Polygon(
                0, -scale,
                scale*t/2, scale/2,
                -scale*t/2, scale/2
        );
    }
    public TriangleBody() {

        shape = getEquiTri(1f);
        getChildren().add(shape);
    }

    @Override
    public boolean intersectsCircle(Point2D center, float radius) {
        return getEquiTri(1+radius).contains(center);
    }
}
