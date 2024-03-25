package com.example.dz1.gunman.body;

import javafx.scene.shape.Circle;

public class CircleBody extends Body {
    public CircleBody() {
        shape = new Circle(1f);
        this.getChildren().addAll(shape);
    }
}
