package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;

public class MapManager {
    public static MapManager instance;
    GameMap gameMap;

    public MapManager(float width, float height) {
        gameMap = new GameMap((int) width / Constant.BLOCK_SIZE, (int) height / Constant.BLOCK_SIZE, 0);
        instance = this;
    }
}