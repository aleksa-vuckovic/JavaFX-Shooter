package com.example.dz1.gunman;

import com.example.dz1.field.Field;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Game extends Group {

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private Field field;

    private double mouseX = 0;
    private double mouseY = 0;

    public Game() {
        this.addEventHandler(MouseEvent.ANY, mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
            if (player != null) player.adjustRotate(new Point2D(mouseX,mouseY));
        });
    }
    public void onKeyEvent(KeyEvent keyEvent) {
        if (player != null) {
            switch (keyEvent.getCode()) {
                case UP -> player.moveBy(new Point2D(0, -1));
                case DOWN -> player.moveBy(new Point2D(0, 1));
                case LEFT -> player.moveBy(new Point2D(-1, 0));
                case RIGHT -> player.moveBy(new Point2D(1, 0));
            }
            player.adjustRotate(new Point2D(mouseX, mouseY));
        }
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.getChildren().remove(this.player);
        this.getChildren().add(player);
        this.player = player;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void removeEnemy(Enemy enemy) {
        this.getChildren().remove(enemy);
        enemies.remove(enemy);
    }
    public void addEnemy(Enemy enemy) {
        this.getChildren().addAll(enemy);
        enemies.add(enemy);
    }
    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        this.getChildren().remove(field);
        this.getChildren().add(field);
        this.field = field;
    }

}
