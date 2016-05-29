package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.BaseActor;

public class MapManager {
    public static MapManager instance;
    GameMap gameMap;

    public MapManager(float width, float height) {
        gameMap = new GameMap((int) width / Constant.BLOCK_SIZE, (int) height / Constant.BLOCK_SIZE, 0);
        instance = this;
    }

    public static GameMap getInstance() {
        return instance.gameMap;
    }

    public static void changeBlock(BaseActor actor) {
        instance.gameMap.changeBlock(actor);
    }
}