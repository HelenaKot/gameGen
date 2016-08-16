package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.BaseActor;

import java.util.ArrayList;

public class MapManager {
    public static MapManager instance;
    MapType gameMap;

    public MapManager(float width, float height, MapType mapClass) {
        Constant.setUpBlockConstants((int) width);
        gameMap = mapClass.init((int) width / Constant.BLOCK_SIZE, (int) height / Constant.BLOCK_SIZE, 0);
        instance = this;
    }

    public static MapType getInstance() {
        return instance.gameMap;
    }

    public static void changeBlock(BaseActor actor) {
        instance.gameMap.changeBlock(actor);
    }

    public static ArrayList<BaseActor> getBlocksOfClass(String className) {
        ArrayList<BaseActor> output = new ArrayList<>();
        for (BaseActor[] row : getInstance().getMap())
            for (BaseActor actor : row)
                if (actor.getClassName().equals(className))
                    output.add(actor);
        return output;
    }
}