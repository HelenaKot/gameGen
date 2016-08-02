package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.BaseActor;

import java.util.ArrayList;

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

    public static ArrayList<BaseActor> getBlocksOfClass(String className) {
        ArrayList<BaseActor> output = new ArrayList<>();
        for (BaseActor[] row : getInstance().map)
            for (BaseActor actor : row)
                if (actor.getClassName().equals(className))
                    output.add(actor);
        return output;
    }
}