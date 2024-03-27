package com.example.dz1.ui;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Button extends Group {

    public Button(String text, Runnable onClick) {
        float pad = 7f;
        Text t = new Text(text);
        t.setFill(Color.BLACK);
        t.setFont(Font.font("Arial", 20));
        double textWidth = t.getLayoutBounds().getWidth();
        double textHeight = t.getLayoutBounds().getHeight();
        Rectangle rect = new Rectangle(textWidth + 2*pad, textHeight + pad);
        rect.setArcWidth(textWidth/4);
        rect.setArcHeight(textHeight/4);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(3f);
        rect.setStrokeType(StrokeType.OUTSIDE);
        t.setTranslateX(pad);
        t.setTranslateY(textHeight);
        getChildren().addAll(rect, t);

        this.setOnMouseClicked(mouseEvent -> onClick.run());
        this.setOnMousePressed(mouseEvent -> {
            rect.setFill(Color.LIGHTGRAY);
        });
        this.setOnMouseReleased(mouseEvent -> {
            rect.setFill(Color.WHITE);
        });
    }
}
