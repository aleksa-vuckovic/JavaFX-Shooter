package com.example.dz1.indicators;

import com.example.dz1.IntervalTimer;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

import java.time.format.DateTimeFormatter;

public class TimeIndicator extends Group {

    private static float PAD = 10f;
    private static float FONT_SIZE = 24f;
    Text text;
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
        text = new Text("00:00");
        text.setTranslateX(PAD);
        text.setTranslateY(PAD + FONT_SIZE);
        text.setFont(Font.font("Arial", FONT_SIZE)); // Set font size to 24
        getChildren().addAll(text);
        this.position = new Translate();
        getTransforms().addAll(this.position);
        this.timer = new IntervalTimer(IntervalTimer.Precision.NANO) {
            @Override
            public void handleInterval(long interval) {
                time += interval;
                text.setText(format(time));
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
