package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.BaseActor;

import java.util.ArrayList;

public class MapManager {
    public static MapManager gameInstance;
    public MapType gameMap;

    public MapManager(MapType mapClass) {
        gameMap = mapClass.init();
        if (mapClass instanceof GameMap)
            gameInstance = this;
    }

    public void setBoard(Board board) {
        gameMap.setBoard(board);
    }

    public static void changeBlock(BaseActor actor) {
        gameInstance.gameMap.changeBlock(actor);
    }

    public static ArrayList<BaseActor> getBlocksOfClass(String className) {
        ArrayList<BaseActor> output = new ArrayList<>();
        for (BaseActor[] row : gameInstance.gameMap.getMap())
            for (BaseActor actor : row)
                if (actor.getClassName().equals(className))
                    output.add(actor);
        return output;
    }
}