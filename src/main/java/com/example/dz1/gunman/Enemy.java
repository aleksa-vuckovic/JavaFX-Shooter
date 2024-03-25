package com.example.dz1.gunman;

import com.example.dz1.field.Field;
import com.example.dz1.gunman.body.Body;
import com.example.dz1.gunman.gun.Gun;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends Gunman {


    public Enemy(Body body, Gun gun, float radius, Game game) {
        super(body, gun, radius, game);
    }


}
