package com.example.dz1.gunman;

import com.example.dz1.Game;
import com.example.dz1.IntervalTimer;
import com.example.dz1.Utils;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.CircleBody;
import com.example.dz1.gunman.gun.ConeGun;
import com.example.dz1.gunman.gun.Gun;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Enemy extends Gunman {

    /**
     * Number of bullets fired per second.
     */
    private float fireFrequency;
    private IntervalTimer fireTimer;

    protected int maxLives;
    protected int lives;


    public Enemy(Body body, Gun gun, int lives, float fireFrequency) {
        super(body, gun);
        this.fireFrequency = fireFrequency;
        this.maxLives = this.lives = lives;
    }
    public static Enemy regularEnemy(int lives) {
        Body body = new CircleBody();
        body.setFill(Color.RED);
        body.setStroke(Color.PURPLE);
        Gun gun = new ConeGun();
        gun.setFill(Color.PURPLE);
        Enemy enemy = new Enemy(body, gun, lives, 0.1f);
        enemy.setRadius(20f);
        return enemy;
    }

    @Override
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        if (!super.interact(bullet, onRemoveBullet, onRemoveGunman)) return false;
        onRemoveBullet.run();
        lives -= bullet.getDamage();
         if (lives <= 0) onRemoveGunman.run();
         else {
             Paint paint = body.getFill();
             if (paint instanceof Color color) {
                 body.setFill(Utils.changeOpacity(color,lives/(float)maxLives));
             }
         }
        return true;
    }

    @Override
    public void fire() {
        super.fireBullet(Bullet.slowBullet());
        super.trigger();
    }

    @Override
    public void finish() {
        super.finish();
        if (fireTimer != null) {
            fireTimer.stop();
            fireTimer = null;
        }
    }

    @Override
    public void start(Game game) {
        super.start(game);
        this.fireTimer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                float probability = fireFrequency/1000 * interval;
                if (Math.random() < probability) fire();
            }
        };
        fireTimer.start();
    }
}
