package com.example.dz1.gunman.body;

import javafx.scene.shape.Polygon;

public class HexagonBody extends Body {

    public HexagonBody() {
        shape = new Polygon(
                0.4f, -1f,
                1f, 0f,
                0.4f, 1f,
                -0.4f, 1f,
                -1f, 0f,
                -0.4f, -1f
        );
        this.getChildren().addAll(shape);
    }
}
