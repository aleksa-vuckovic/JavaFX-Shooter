package com.example.dz1.indicators;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class BulletIndicator extends Group {
    private static float R = 10f;
    private static float PAD = 10f;

    int max;
    int count;
    public BulletIndicator(int max) {
        this.max = max;
        this.count = 0;
        for (int i = 0; i < max; i++) inc();
    }
    public void inc() {
        if (count == max) return;
        Circle ind = new Circle(R, Color.RED);
        ind.setCenterY(PAD + R);
        ind.setCenterX(-(PAD + R + count*3*R));
        ind.setFill(Color.RED);
        getChildren().add(0, ind);
        count++;
    }
    public boolean dec() {
        if (count == 0) return false;
        count--;
        getChildren().remove(0);
        return true;
    }
    public void setPosition(Point2D position) {
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
    }
}
