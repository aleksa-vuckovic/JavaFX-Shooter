package com.example.dz1;

import com.example.dz1.field.Field;
import com.example.dz1.gunman.Bullet;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Player;
import com.example.dz1.indicators.BulletIndicator;
import com.example.dz1.indicators.LivesIndicator;
import com.example.dz1.indicators.TimeIndicator;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class Game extends Group {

    private Field field;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new LinkedList<>();

    private Bounds gameBounds;
    private TimeIndicator timeIndicator;
    private IntervalTimer timer;

    enum State {
        PLAYING,
        OVER
    }
    private State state = State.PLAYING;
    private Point2D mouse = Point2D.ZERO;

    public Game(float sceneWidth, float sceneHeight) {
        this.gameBounds = new Rectangle(-sceneWidth/2, -sceneHeight/2, sceneWidth, sceneHeight).getBoundsInLocal();
    }
    public void onMouseEvent(MouseEvent mouseEvent) {
        if (state != State.PLAYING) return;
        this.mouse = this.parentToLocal(mouseEvent.getX(), mouseEvent.getY());
        player.adjustRotate(mouse);

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) player.fireBig();
            else player.fire();
        }
    }
    private Set<KeyCode> controls = new HashSet<>();
    public void onKeyEvent(KeyEvent keyEvent) {
        if (state != State.PLAYING) return;
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) controls.add(keyEvent.getCode());
        else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) controls.remove(keyEvent.getCode());
        Point2D direction = Point2D.ZERO;
        for (KeyCode code: controls) {
            switch (code) {
                case UP -> direction = direction.add(new Point2D(0, -1));
                case DOWN -> direction = direction.add(new Point2D(0, 1));
                case LEFT -> direction = direction.add(new Point2D(-1, 0));
                case RIGHT -> direction = direction.add(new Point2D(1, 0));
            }
        }
        direction = direction.normalize();
        player.setDirection(direction);
        player.adjustRotate(mouse);
    }

    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        getChildren().remove(this.field);
        getChildren().add(field);
        this.field = field;
    }
    public void setPlayer(Player player) {
        if (this.player != null) {
            getChildren().remove(this.player);
            getChildren().remove(this.player.getBulletIndicator());
            getChildren().remove(this.player.getLivesIndicator());
            this.player.finish();
            getChildren().removeAll(bullets);
            bullets.clear();
        }
        BulletIndicator bulletIndicator = player.getBulletIndicator();
        bulletIndicator.setPosition(Utils.getUpperRight(gameBounds));
        LivesIndicator livesIndicator = player.getLivesIndicator();
        livesIndicator.setPosition(Utils.getLowerLeft(gameBounds));
        this.player = player;
        player.start(this);
        this.getChildren().addAll(player, bulletIndicator, livesIndicator);
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void addEnemy(Enemy enemy) {
        this.getChildren().addAll(enemy);
        enemies.add(enemy);
        enemy.adjustRotate(player);
        enemy.start(this);
    }
    public void addBullet(Bullet bullet) {
        this.getChildren().add(bullet);
        bullets.add(bullet);
    }

    /**
     * Checks whether the given bounds are entirely outside the bounds of the game (scene).
     * @param bounds The bounds in game coordinates.
     */
    public boolean isOutside(Bounds bounds) {
        return !this.gameBounds.intersects(bounds);
    }

    public void timeUpdate(long interval) {
        if (state != State.PLAYING) return;
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
                        enemy.finish();
                    });
                    if (removed.value) break;
                }
                if (!removed.value) player.interact(bullet, bulletRemover, this::finish);
            }
        }
    }

    public void start() {
        timer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                timeUpdate(interval);
            }
        };
        timer.start();

        if (timeIndicator != null) {
            getChildren().remove(timeIndicator);
            timeIndicator = null;
        }
        timeIndicator = new TimeIndicator();
        timeIndicator.setPosition(Utils.getUpperLeft(gameBounds));
        getChildren().addAll(timeIndicator);
        timeIndicator.start();
        state = State.PLAYING;
    }
    public void finish() {
        if (state != State.PLAYING) return;
        state = State.OVER;
        for (Enemy enemy: enemies) enemy.finish();
        player.finish();
        timeIndicator.stop();
        timer.stop();
        timer = null;

        Text text = new Text("GAME OVER");
        text.setFont(Font.font("Arial", 70));
        text.setTranslateX(-text.getBoundsInLocal().getCenterX());
        text.setTranslateY(-text.getBoundsInLocal().getCenterY());
        Rectangle cover = new Rectangle(gameBounds.getMinX(), gameBounds.getMinY(), gameBounds.getWidth(), gameBounds.getHeight());
        cover.setFill(Utils.changeOpacity(Color.WHITE, 0.5f));
        getChildren().addAll(cover, text);
    }

}
