package com.example.dz1.gunman;

import com.example.dz1.Game;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.gun.Gun;
import com.example.dz1.indicators.BulletIndicator;
import com.example.dz1.indicators.LivesIndicator;

public class Player extends Gunman {

    private static final float SPEED = 5f;
    private BulletIndicator bulletIndicator;
    private LivesIndicator livesIndicator;

    public Player(Body body, Gun gun, float radius, int lives, int bigBullets, Game game) {
        super(body, gun, radius, game);
        this.livesIndicator = new LivesIndicator(lives);
        this.bulletIndicator = new BulletIndicator(bigBullets);
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




}
