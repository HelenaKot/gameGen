package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;

import java.util.HashMap;

public class BoardManager {
    static BoardManager instance;
    HashMap<String, Board> boards;

    public BoardManager() {
        boards = new HashMap<>();
        instance = this;
    }

    private static BoardManager getInstance() {
        return (instance != null) ? instance : new BoardManager();
    }

    public static void setInstance(HashMap<String, Board> boardMap) {
        if (boardMap != null) getInstance().boards = new HashMap<>(boardMap);
        else getInstance().boards = new HashMap<>();
    }

    public static void addBoard(String name, Board board) {
        getInstance().boards.put(name, board);
    }

    public static HashMap<String, Board> getBoards() {
        return getInstance().boards;
    }

    public static Board get(String key) {
        if (!getInstance().boards.containsKey(key))
            getInstance().boards.put(key, new Board(Constant.MAP_WIDTH, Constant.MAP_HEIGHT, ActorInitializer.getActorTile("empty")));
        return getInstance().boards.get(key);
    }

    public static void dispose() {
        instance = null;
    }
}
