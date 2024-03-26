package com.example.dz1;

import javafx.animation.AnimationTimer;

public abstract class IntervalTimer extends AnimationTimer {
    long prev = 0;
    @Override
    public final void handle(long now) {
        now /= 1000000;
        if (prev == 0) prev = now;
        else if (prev == now) return;
        handleInterval(now - prev);
        prev = now;
    }
    public abstract void handleInterval(long interval);
}
