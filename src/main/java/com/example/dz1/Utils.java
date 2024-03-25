package com.example.dz1;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class Utils {

    public static Point2D[] getCorners(Bounds bounds) {
        return new Point2D[] {
                new Point2D(bounds.getMinX(), bounds.getMinY()),
                new Point2D(bounds.getMinX(), bounds.getMaxY()),
                new Point2D(bounds.getMaxX(), bounds.getMinY()),
                new Point2D(bounds.getMaxX(), bounds.getMaxY()),
        };
    }
}
