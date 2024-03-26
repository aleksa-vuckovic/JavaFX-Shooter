package com.example.dz1;

import javafx.animation.AnimationTimer;

public abstract class IntervalTimer extends AnimationTimer {
    long prev = 0;
    long div = 0;

    public enum Precision {
        NANO, MICRO, MILLI, SEC
    }
    public IntervalTimer(Precision precision) {
        switch (precision) {
            case NANO -> div = 1;
            case MICRO -> div = 1000;
            case MILLI -> div = 1000000;
            case SEC -> div = 1000000000;
        }
    }
    public IntervalTimer() {this(Precision.MILLI);}
    @Override
    public final void handle(long now) {
        now /= div;
        if (prev == 0) prev = now;
        else if (prev == now) return;
        handleInterval(now - prev);
        prev = now;
    }
    public abstract void handleInterval(long interval);
}
