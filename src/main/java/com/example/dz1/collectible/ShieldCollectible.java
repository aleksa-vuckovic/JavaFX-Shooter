package com.example.dz1.collectible;

import com.example.dz1.Utils;
import com.example.dz1.gunman.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ShieldCollectible extends Collectible {

    public ShieldCollectible() {
        Polygon shield = Utils.getHexagon(10);
        shield.setFill(Color.STEELBLUE);
        shield.setStroke(Color.LIGHTBLUE);
        getChildren().add(shield);
    }

    @Override
    public void collect(Player player, Runnable onDisappear) {
        player.setShield();
        super.collect(player, onDisappear);
    }
}
