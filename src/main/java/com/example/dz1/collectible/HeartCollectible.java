package com.example.dz1.collectible;

import com.example.dz1.gunman.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class HeartCollectible extends Collectible {

    public HeartCollectible() {
        Path heart = new Path(
                new MoveTo(0,10),
                new CubicCurveTo(-20, -2, -6, -18, 0, -4),
                new CubicCurveTo(6, -18, 20, -2, 0, 10)
        );
        heart.setFill(Color.RED);
        getChildren().addAll(heart);
    }

    @Override
    public void collect(Player player, Runnable onDisappear) {
        player.getLivesIndicator().inc();
        super.collect(player, onDisappear);
    }
}
