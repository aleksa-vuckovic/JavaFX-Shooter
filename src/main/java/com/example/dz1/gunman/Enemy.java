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

    private int lives;
    private int maxLives;
    /**
     * Number of bullets fired per second.
     */
    private float fireFrequency;
    private IntervalTimer fireTimer;


    public Enemy(Body body, Gun gun, float radius, int lives, float fireFrequency, Game game) {
        super(body, gun, radius, game);
        this.lives = lives;
        this.maxLives = lives;
        this.fireFrequency = fireFrequency;

        this.fireTimer = new IntervalTimer() {
            @Override
            public void handleInterval(long interval) {
                float probability = fireFrequency/1000 * interval;
                if (Math.random() < probability) fire();
            }
        };
        fireTimer.start();

    }
    public static Enemy regularEnemy(Game game) {
        Body body = new CircleBody();
        body.setFill(Color.RED);
        body.setStroke(Color.PURPLE);
        Gun gun = new ConeGun();
        gun.setFill(Color.PURPLE);
        return new Enemy(body, gun, 20f, 4, 0.2f, game);
    }

    @Override
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        if (!super.interact(bullet, onRemoveBullet, onRemoveGunman)) return false;
        onRemoveBullet.run();
        lives -= bullet.getDamage();
         if (lives <= 0) {
             onRemoveGunman.run();
             fireTimer.stop();
         }
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
}
