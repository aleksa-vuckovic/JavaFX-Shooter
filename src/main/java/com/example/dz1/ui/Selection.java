package com.example.dz1.ui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class Selection<T extends Node> extends Group {

    T selected;
    Rectangle selectedFrame;

    public Selection(List<T> options, double size, String title) {
        double pad = size/10;
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", 30));
        titleText.setFill(Color.WHITE);
        double textHeight = titleText.getBoundsInLocal().getHeight();
        titleText.setTranslateY(pad + textHeight);
        titleText.setTranslateX(pad);
        getChildren().addAll(titleText);
        double optionsTranslateY = pad + textHeight + pad;

        for (int i = 0; i < options.size(); i++) {
            Group g = new Group();
            Rectangle frame = new Rectangle(-size/2, -size/2, size, size);
            frame.setFill(Color.LIGHTGRAY);
            frame.setArcWidth(size/10);
            frame.setArcHeight(size/10);
            frame.setStrokeWidth(size/20);
            frame.setStrokeType(StrokeType.INSIDE);
            frame.setStroke(null);
            g.getChildren().addAll(frame, options.get(i));

            g.setTranslateY(size/2 + optionsTranslateY);
            g.setTranslateX(size/2 + pad + i*(size+pad));
            int finalI = i;
            g.setOnMouseClicked(mouseEvent -> {
                select(options.get(finalI), frame);
            });
            if (i == 0) select(options.get(i), frame);

            getChildren().addAll(g);
        }
    }

    private void select(T selected, Rectangle selectedFrame) {
        if (this.selected != null) this.selectedFrame.setStroke(null);
        this.selected = selected;
        this.selectedFrame = selectedFrame;
        selectedFrame.setStroke(Color.WHITE);
    }

    public T getSelected() {
        return selected;
    }
}
