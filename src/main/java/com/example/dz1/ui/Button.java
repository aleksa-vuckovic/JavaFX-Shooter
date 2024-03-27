package com.example.dz1.ui;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Button extends TextBox {

    public Button(String text, Runnable onClick) {
        super(text, 10f, Font.font("Arial", 20), 0.25);
        setColor(Color.BLACK);
        setBackground(new LinearGradient(0,0,0,1,true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.LIGHTGRAY), new Stop(1, Color.WHITE)
        }));
        setBorder(Color.GRAY, 3f);
        setOnClick(onClick);
    }
}
