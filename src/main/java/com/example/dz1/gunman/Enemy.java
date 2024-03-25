package com.example.dz1.gunman;

import com.example.dz1.Utils;
import com.example.dz1.field.Field;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.body.CircleBody;
import com.example.dz1.gunman.gun.ConeGun;
import com.example.dz1.gunman.gun.Gun;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Enemy extends Gunman {

    private int lives;
    private int maxLives;

    public Enemy(Body body, Gun gun, float radius, int lives, Game game) {
        super(body, gun, radius, game);
        this.lives = lives;
        this.maxLives = lives;
    }
    public static Enemy regularEnemy(Game game) {
        Body body = new CircleBody();
        body.setFill(Color.RED);
        body.setStroke(Color.PURPLE);
        Gun gun = new ConeGun();
        gun.setFill(Color.PURPLE);
        return new Enemy(body, gun, 20f, 4, game);
    }

    @Override
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        if (!super.interact(bullet, onRemoveBullet, onRemoveGunman)) return false;
        onRemoveBullet.run();
         if (--lives <= 0) onRemoveGunman.run();
         else {
             Paint paint = body.getFill();
             if (paint instanceof Color color) {
                 body.setFill(Utils.changeOpacity(color,lives/(float)maxLives));
             }
         }
        return true;
    }

}
