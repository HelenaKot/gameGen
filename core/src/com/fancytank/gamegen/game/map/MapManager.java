package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.BaseActor;

import java.util.ArrayList;

public class MapManager {
    public static MapManager instance;
    MapType gameMap;

    public MapManager(MapType mapClass) {
        gameMap = mapClass.init();
        instance = this;
    }

    public static MapType getMap() {
        return instance.gameMap;
    }

    public static void setBoard(Board board) {
        getMap().setBoard(board);
    }

    public static void changeBlock(BaseActor actor) {
        instance.gameMap.changeBlock(actor);
    }

    public static ArrayList<BaseActor> getBlocksOfClass(String className) {
        ArrayList<BaseActor> output = new ArrayList<>();
        for (BaseActor[] row : getMap().getMap())
            for (BaseActor actor : row)
                if (actor.getClassName().equals(className))
                    output.add(actor);
        return output;
    }
}