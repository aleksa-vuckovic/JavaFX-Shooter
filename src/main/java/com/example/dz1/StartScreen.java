package com.example.dz1;

import com.example.dz1.field.Field;
import com.example.dz1.gunman.Player;
import com.example.dz1.ui.Button;
import com.example.dz1.ui.Selection;
import com.example.dz1.ui.TextBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class StartScreen extends Group {

    private Selection playerSelection;
    private Selection fieldSelection;
    private Selection difficultySelection;
    private Button start;
    private Runnable onStart;

    public StartScreen() {}

    public Player getPlayer() {
        return (Player) playerSelection.getSelected();
    }
    public Field getField() {
        return (Field) fieldSelection.getSelected();
    }
    public String getDifficulty() {
        return ((TextBox)difficultySelection.getSelected()).getContent();
    }
    public void setOnStart(Runnable onStart) {this.onStart = onStart;}

    public void reset() {
        final double w = 100;
        getChildren().clear();

        Player player1 = Player.regular();
        Player player2 = Player.slow();
        Player player3 = Player.fast();
        List<Player> playerOptions = new ArrayList<>();
        playerOptions.add(player1); playerOptions.add(player2); playerOptions.add(player3);
        playerSelection = new Selection(playerOptions, w, w, "Select your tank:");

        Field field1 = Field.islandField(); field1.setDimensions(w,w);
        Field field2 = Field.bridgeField(); field2.setDimensions(w,w);
        Field field3 = Field.starField(); field3.setDimensions(w,w);
        List<Field> fieldOptions = new ArrayList<>();
        fieldOptions.add(field1); fieldOptions.add(field2); fieldOptions.add(field3);
        fieldSelection = new Selection(fieldOptions, w, w, "Select stage:");

        Font font = Font.font("Arial", FontWeight.BOLD, 24);
        TextBox easy = new TextBox("Easy", 0f, font, 0.1);
        TextBox medium = new TextBox("Medium", 0f, font, 0.1);
        TextBox hard = new TextBox("Hard", 0f, font, 0.1);
        easy.setBackground(Color.LIGHTGREEN);
        medium.setBackground(Color.LIGHTYELLOW);
        hard.setBackground(Color.PALEVIOLETRED);
        List<TextBox> options = new ArrayList<>();
        options.add(easy); options.add(medium); options.add(hard);
        for (TextBox option: options) {
            option.setWidth(100.0);
            option.setHeight(50.0);
            option.setBorder(null, 0);
            option.setTranslateX(-50);
            option.setTranslateY(-25);
        }
        difficultySelection = new Selection(options, 100, 50, "Select difficulty:");

        start = new Button("Start", onStart);

        getChildren().addAll(playerSelection, fieldSelection, difficultySelection, start);
        fieldSelection.setTranslateY(w*1.5);
        difficultySelection.setTranslateY(w*3.2);
        start.setTranslateY(4.5*w);
        start.setTranslateX(20);
    }

}
