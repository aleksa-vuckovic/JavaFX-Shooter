package com.example.dz1.gunman;

import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.gun.Gun;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Player extends Gunman {

    private static final float SPEED = 5f;

    public Player(Body body, Gun gun, float radius, Game game) {
        super(body, gun, radius, game);
    }

    @Override
    public boolean interact(Bullet bullet, Runnable onRemoveBullet, Runnable onRemoveGunman) {
        if (!super.interact(bullet, onRemoveBullet, onRemoveGunman)) return false;
        onRemoveBullet.run();
        return true;
    }

}
