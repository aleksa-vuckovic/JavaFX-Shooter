package com.example.dz1.gunman;

import com.example.dz1.Wrapper;
import com.example.dz1.field.Field;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Game extends Group {

    private Field field;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new LinkedList<>();
    private Bounds gameBounds;

    private Point2D mouse = Point2D.ZERO;

    public Game(float sceneWidth, float sceneHeight) {
        this.gameBounds = new Rectangle(-sceneWidth/2, -sceneHeight/2, sceneWidth, sceneHeight).getBoundsInLocal();
    }
    public void onMouseEvent(MouseEvent mouseEvent) {
        this.mouse = this.parentToLocal(mouseEvent.getX(), mouseEvent.getY());
        player.adjustRotate(mouse);

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
            player.fire();
        }
    }
    public void onKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP -> player.moveBy(new Point2D(0, -1));
            case DOWN -> player.moveBy(new Point2D(0, 1));
            case LEFT -> player.moveBy(new Point2D(-1, 0));
            case RIGHT -> player.moveBy(new Point2D(1, 0));
        }
        player.adjustRotate(mouse);
        for (Enemy enemy: enemies) enemy.adjustRotate(player);
    }

    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        this.getChildren().remove(field);
        this.getChildren().add(field);
        this.field = field;
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
        enemy.adjustRotate(player);
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
    public void addBullet(Bullet bullet) {
        this.getChildren().add(bullet);
        bullets.add(bullet);
    }
    /*
    public void removeBullet(Bullet bullet) {
        this.getChildren().remove(bullet);
        bullets.remove(bullet);
    }
    */

    /**
     * Checks whether the given bounds are entirely outside the bounds of the game (scene).
     * @param bounds The bounds in game coordinates.
     */
    public boolean isOutside(Bounds bounds) {
        return !this.gameBounds.intersects(bounds);
    }

    public void timeUpdate(long interval) {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.timeUpdate(interval);
            if (isOutside(bullet.getBoundsInParent())) {
                bulletIter.remove();
                this.getChildren().remove(bullet);
            }
            else {
                Iterator<Enemy> enemyIter = enemies.iterator();
                Wrapper<Boolean> removed = new Wrapper<>(false);
                Runnable bulletRemover = () -> {
                    bulletIter.remove();
                    getChildren().remove(bullet);
                    removed.value = true;
                };
                while(enemyIter.hasNext()) {
                    Enemy enemy = enemyIter.next();
                    enemy.interact(bullet, bulletRemover, () -> {
                        enemyIter.remove();
                        getChildren().remove(enemy);
                    });
                    if (removed.value) break;
                }
                if (!removed.value) player.interact(bullet, bulletRemover, () -> {
                    //Game over?
                });
            }
        }
    }

}
