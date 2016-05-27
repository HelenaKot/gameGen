package com.fancytank.gamegen.generated.map;

import com.fancytank.gamegen.generated.Constant;

public class MapManager {
    public static MapManager instance;
    GameMap gameMap;

    public MapManager(float width, float height) {
        gameMap = new GameMap((int) width / Constant.BLOCK_SIZE, (int) height / Constant.BLOCK_SIZE, 0);
        instance = this;
    }
}