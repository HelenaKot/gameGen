package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;

import java.util.ArrayList;

public class MapManager {
    public static MapManager instance;
    public MapType gameMap;
    private static BaseActor nullObject;

    public MapManager(MapType mapClass) {
        gameMap = mapClass.init();
        instance = this;
        nullObject = ActorInitializer.getInstanceOf("unspecified", -999, -999);
    }

    public static void setBoard(Board board) {
        instance.gameMap.setBoard(board);
    }

    public static void changeBlock(BaseActor actor) {
        instance.gameMap.changeBlock(actor);
    }

    public static BaseActor getBlock(int x, int y) {
        if (0 <= x && x < instance.gameMap.getMap().length && 0 <= y && y < instance.gameMap.getMap()[0].length)
            return instance.gameMap.getMap()[x][y];
        return nullObject;
    }

    public static ArrayList<BaseActor> getBlocksOfClass(String className) {
        ArrayList<BaseActor> output = new ArrayList<>();
        for (BaseActor[] row : instance.gameMap.getMap())
            for (BaseActor actor : row)
                if (actor.getClassName().equals(className))
                    output.add(actor);
        return output;
    }

    public static void dispose() {
        instance = null;
    }
}