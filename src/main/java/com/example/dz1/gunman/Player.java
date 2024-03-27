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

import java.util.Optional;

public class Player extends Gunman {

    /**
     * The speed in pixels per millisecond.
     */
    private float speed;
    private BulletIndicator bulletIndicator;
    private LivesIndicator livesIndicator;
    private Point2D direction = Point2D.ZERO;

    private IntervalTimer moveTimer;


    public Player(Body body, Gun gun, int lives, int bigBullets, float speed, Game game) {
        super(body, gun, game);
        this.livesIndicator = new LivesIndicator(lives);
        this.bulletIndicator = new BulletIndicator(bigBullets);
        this.speed = speed;
    }
    public static Player regular(Game game) {
        Body playerBody = new HexagonBody();
        playerBody.setFill(Color.YELLOW);
        playerBody.setStroke(Color.PURPLE);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.PURPLE);
        return new Player(playerBody, playerGun, 4, 4, Utils.SPEED_MEDIUM, game);
    }
    public static Player slow(Game game) {
        Body playerBody = new SquareBody();
        playerBody.setFill(Color.BLUE);
        playerBody.setStroke(Color.BLACK);

        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.BLACK);
        return new Player(playerBody, playerGun, 7, 4, Utils.SPEED_SLOW, game);
    }
    public static Player fast(Game game) {
        Body playerBody = new TriangleBody();
        playerBody.setFill(Color.AQUAMARINE);
        playerBody.setStroke(Color.LIGHTGREEN);
        Gun playerGun = new RegularGun();
        playerGun.setFill(Color.LIGHTGREEN);
        return new Player(playerBody, playerGun, 2, 4, Utils.SPEED_FAST, game);
    }


    public BulletIndicator getBulletIndicator() {
        return bulletIndicator;
    }
    public LivesIndicator getLivesIndicator() {
        return livesIndicator;
    }

    @Override
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        if (!super.interact(bullet, onRemoveBullet, onRemoveGunman)) return false;
        onRemoveBullet.run();
        livesIndicator.set(livesIndicator.get() - bullet.getDamage());
        if (livesIndicator.dead()) onRemoveGunman.run();
        return true;
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
        if (!game.getField().isWithinBounds(this)) {
            this.position.setX(this.position.getX() - amount.getX());
            this.position.setY(this.position.getY() - amount.getY());
            return false;
        }
        return true;
    }

    @Override
    public void die() {
        if (this.moveTimer != null) {
            this.moveTimer.stop();
            this.moveTimer = null;
        }
    }

    @Override
    public void start() {
        this.moveTimer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                if (direction.equals(Point2D.ZERO)) return;
                Point2D amount = direction.multiply(interval*speed);
                if (moveBy(amount)) for (Enemy enemy: game.getEnemies()) enemy.adjustRotate(Player.this);
            }
        };
        this.moveTimer.start();
    }

}
