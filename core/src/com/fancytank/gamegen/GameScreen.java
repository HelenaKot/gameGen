package com.fancytank.gamegen;

import com.fancytank.gamegen.generated.Constant;
import com.fancytank.gamegen.generated.map.MapManager;

public class GameScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        Constant.setUpBlockConstants((int) getWidth(), 10);
        new MapManager(getWidth(), getHeight());
    }
}
