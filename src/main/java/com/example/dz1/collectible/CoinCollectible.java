package com.example.dz1.collectible;

import com.example.dz1.gunman.Player;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CoinCollectible extends Collectible {


    public CoinCollectible() {
        Circle coin = new Circle(10);
        Image goldImage = new Image("gold.jpg");
        ImagePattern goldPattern = new ImagePattern(goldImage);
        coin.setFill(goldPattern);
        getChildren().addAll(coin);
    }

    @Override
    public void collect(Player player, Runnable onDisappear) {
        player.getBulletIndicator().inc();
        super.collect(player, onDisappear);
    }
}
