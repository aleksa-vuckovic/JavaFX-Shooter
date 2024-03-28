package com.example.dz1;

import com.example.dz1.collectible.CoinCollectible;
import com.example.dz1.collectible.Collectible;
import com.example.dz1.collectible.HeartCollectible;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.Bullet;
import com.example.dz1.gunman.Enemy;
import com.example.dz1.gunman.Player;
import com.example.dz1.indicators.BulletIndicator;
import com.example.dz1.indicators.LivesIndicator;
import com.example.dz1.indicators.TimeIndicator;
import com.example.dz1.ui.Button;
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

    private static double COLLECTIBLE_PROBABILITY = 0.1;
    private static double COIN_PROBABILITY = 0;
    private static double SHIELD_PROBABILITY = 0;
    private static final double HEART_PROBABILITY = 1;

    private Runnable onBack;
    private Field field;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new LinkedList<>();
    private List<Collectible> collectibles = new ArrayList<Collectible>();

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
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
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
        //Checking collectibles
        Iterator<Collectible> collectibleIter = collectibles.iterator();
        while (collectibleIter.hasNext()) {
            Collectible cur = collectibleIter.next();
            if (player.interacts(cur.getPosition())) {
                cur.collect(player, () -> {
                    getChildren().remove(cur);
                });
                collectibleIter.remove();
            }
        }
    }

    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        getChildren().add(field);
        this.field = field;
    }
    public void setPlayer(Player player) {
        BulletIndicator bulletIndicator = player.getBulletIndicator();
        bulletIndicator.setPosition(Utils.getUpperRight(gameBounds));
        LivesIndicator livesIndicator = player.getLivesIndicator();
        livesIndicator.setPosition(Utils.getLowerLeft(gameBounds));
        this.player = player;
        this.getChildren().addAll(player, bulletIndicator, livesIndicator);
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void addEnemy(Enemy enemy) {
        this.getChildren().addAll(enemy);
        enemies.add(enemy);
        enemy.adjustRotate(player);
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
        //Checking bullet hits
        Iterator<Bullet> bulletIter = bullets.iterator();
        outer: while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.timeUpdate(interval);
            if (isOutside(bullet.getBoundsInParent())) {
                bulletIter.remove();
                getChildren().remove(bullet);
                continue;
            }
            Iterator<Enemy> enemyIter = enemies.iterator();
            while(enemyIter.hasNext()) {
                Enemy enemy = enemyIter.next();
                if (enemy.interacts(bullet)) {
                    enemy.take(bullet, () -> {
                        enemyIter.remove();
                        getChildren().remove(enemy);
                        enemy.finish();
                    });
                    bulletIter.remove();
                    getChildren().remove(bullet);
                    continue outer;
                }
            }
            if (player.interacts(bullet)) {
                player.take(bullet, this::finish);
                bulletIter.remove();
                getChildren().remove(bullet);
            }
        }
        if (enemies.isEmpty()) finish();
        //Generating collectibles
        if (Math.random() < COLLECTIBLE_PROBABILITY/1000*interval) {
            Collectible collectible;
            double rand = Math.random();
            if (rand < COIN_PROBABILITY) collectible = new CoinCollectible();
            else if (rand < COIN_PROBABILITY + HEART_PROBABILITY) collectible = new HeartCollectible();
            else collectible = new HeartCollectible();
            Point2D location = field.getRandomPlatformPoint();
            collectible.setPosition(location);
            collectibles.add(collectible);
            getChildren().add(1, collectible);
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
        timeIndicator = new TimeIndicator();
        timeIndicator.setPosition(Utils.getUpperLeft(gameBounds));
        getChildren().addAll(timeIndicator);
        timeIndicator.start();
        player.start(this);
        for (Enemy enemy: enemies) enemy.start(this);
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

        Text text = enemies.isEmpty() ? new Text("CONGRATULATIONS!") : new Text("GAME OVER");
        text.setFont(Font.font("Arial", 70));
        text.setTranslateX(-text.getBoundsInLocal().getCenterX());
        text.setTranslateY(-text.getBoundsInLocal().getCenterY());
        Rectangle cover = new Rectangle(gameBounds.getMinX(), gameBounds.getMinY(), gameBounds.getWidth(), gameBounds.getHeight());
        cover.setFill(Utils.changeOpacity(Color.WHITE, 0.5f));
        Button back = new Button("Back", onBack);
        back.setTranslateY(gameBounds.getHeight()/4 - back.getBoundsInLocal().getHeight()/2);
        back.setTranslateX(-back.getBoundsInLocal().getWidth()/2);
        getChildren().addAll(cover, text, back);
    }

    public void clear() {
        getChildren().clear();
        field = null; player = null; timeIndicator = null;
        enemies.clear(); bullets.clear(); collectibles.clear();
    }

}
