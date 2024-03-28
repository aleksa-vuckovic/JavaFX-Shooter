package com.example.dz1.gunman;

import com.example.dz1.Game;
import com.example.dz1.IntervalTimer;
import com.example.dz1.Utils;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.HexagonBody;
import com.example.dz1.gunman.body.SquareBody;
import com.example.dz1.gunman.body.TriangleBody;
import com.example.dz1.gunman.gun.Gun;
import com.example.dz1.gunman.gun.RegularGun;
import com.example.dz1.indicators.BulletIndicator;
import com.example.dz1.indicators.LivesIndicator;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Player extends Gunman {

    private static long SHIELD_TIME = 10000;

    /**
     * The speed in pixels per millisecond.
     */
    private float speed;
    private BulletIndicator bulletIndicator;
    private LivesIndicator livesIndicator;
    private Circle shield;
    private long shieldLeft;
    private Point2D direction = Point2D.ZERO;

    private IntervalTimer timer;


    public Player(Body body, Gun gun, int lives, int bigBullets, float speed) {
        super(body, gun);
        this.livesIndicator = new LivesIndicator(lives);
        this.bulletIndicator = new BulletIndicator(bigBullets);
        this.speed = speed;
    }
    public static Player regular() {
        Body playerBody = new HexagonBody();
        playerBody.setFill(Color.YELLOW);
        playerBody.setStroke(Color.PURPLE);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.PURPLE);
        Player player = new Player(playerBody, playerGun, 4, 4, Utils.SPEED_MEDIUM);
        player.setRadius(20f);
        return player;
    }
    public static Player slow() {
        Body playerBody = new SquareBody();
        playerBody.setFill(Color.BLUE);
        playerBody.setStroke(Color.BLACK);

        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.BLACK);
        Player player = new Player(playerBody, playerGun, 7, 4, Utils.SPEED_SLOW);
        player.setRadius(20f);
        return player;
    }
    public static Player fast() {
        Body playerBody = new TriangleBody();
        playerBody.setFill(Color.AQUAMARINE);
        playerBody.setStroke(Color.GREEN);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.GREEN);
        Player player =  new Player(playerBody, playerGun, 2, 4, Utils.SPEED_FAST);
        player.setRadius(20f);
        return player;
    }


    public BulletIndicator getBulletIndicator() {
        return bulletIndicator;
    }
    public LivesIndicator getLivesIndicator() {
        return livesIndicator;
    }

    @Override
    public void take(Bullet bullet, Runnable onRemoveGunman) {
        if (shield != null) return;
        livesIndicator.set(livesIndicator.get() - bullet.getDamage());
        if (livesIndicator.dead()) onRemoveGunman.run();
    }


    @Override
    public void fire() {
        super.fireBullet(Bullet.regularBullet());
    }

    public void fireBig() {
        if (bulletIndicator.dec()) super.fireBullet(Bullet.bigBullet());
    }

    /**
     * Set the movement direction of the player.
     * Should be a unit vector, or a zero vector.
     */
    public void setDirection(Point2D direction) {
        this.direction = direction;
    }
    private boolean moveBy(Point2D amount) {
        this.position.setX(this.position.getX() + amount.getX());
        this.position.setY(this.position.getY() + amount.getY());
        if (!game.getField().isInside(this)) {
            this.position.setX(this.position.getX() - amount.getX());
            this.position.setY(this.position.getY() - amount.getY());
            return false;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
    }

    @Override
    public void start(Game game) {
        super.start(game);
        this.timer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                if (!direction.equals(Point2D.ZERO)) {
                    Point2D amount = direction.multiply(interval*speed);
                    if (moveBy(amount)) for (Enemy enemy: game.getEnemies()) enemy.adjustRotate(Player.this);
                }
                if (shield != null) {
                    shieldLeft -= interval;
                    if (shieldLeft <= 0) {
                        getChildren().remove(shield);
                        shield = null;
                    }
                }
            }
        };
        this.timer.start();
    }

    public void setShield() {
        if (this.shield != null) return;
        shield = new Circle(1.1);
        shield.setFill(Utils.changeOpacity(Color.STEELBLUE, 0.5f));
        shield.setStroke(Utils.changeOpacity(Color.LIGHTBLUE, 0.5f));
        shield.setStrokeWidth(0.05f);
        getChildren().add(shield);
        shieldLeft = SHIELD_TIME;
    }

}
