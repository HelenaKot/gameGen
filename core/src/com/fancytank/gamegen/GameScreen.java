package com.fancytank.gamegen;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.map.MapManager;

public class GameScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        Constant.setUpBlockConstants((int) getWidth(), 10);
        new MapManager(getWidth(), getHeight());
    }
}
