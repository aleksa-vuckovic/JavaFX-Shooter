package com.example.dz1.indicators;

import com.example.dz1.IntervalTimer;
import com.example.dz1.ui.TextBox;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

import java.time.format.DateTimeFormatter;

public class TimeIndicator extends Group {
    TextBox text;
    private long time = 0;
    private boolean active = false;
    private AnimationTimer timer;
    private Translate position;

    private String format(long timeNano) {
        long secs = timeNano/1000000000;
        long mins = secs/60;
        secs %= 60;
        return "%02d:%02d".formatted(mins, secs);
    }

    public TimeIndicator() {
        text = new TextBox("00:00", 10f, Font.font("Arial", 24f), 0f);
        getChildren().addAll(text);
        this.position = new Translate();
        getTransforms().addAll(this.position);
        this.timer = new IntervalTimer(IntervalTimer.Precision.NANO) {
            @Override
            public void handleInterval(long interval) {
                time += interval;
                text.setContent(format(time));
            }
        };
    }

    public void start() {
        if (active) return;
        active = true;
        timer.start();
    }
    public void stop() {
        if (!active) return;
        active = false;
        timer.stop();
    }

    public void setPosition(Point2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }
}
