package com.example.dz1;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Utils {

    public static Point2D[] getCorners(Bounds bounds) {
        return new Point2D[] {
                new Point2D(bounds.getMinX(), bounds.getMinY()),
                new Point2D(bounds.getMinX(), bounds.getMaxY()),
                new Point2D(bounds.getMaxX(), bounds.getMinY()),
                new Point2D(bounds.getMaxX(), bounds.getMaxY()),
        };
    }

    public static Color changeOpacity(Color color, float opacity) {
        return Color.color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }
}
