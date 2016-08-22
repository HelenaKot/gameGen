package com.fancytank.gamegen;

import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.MapManager;

public class DesignScreen extends AbstractScreen {
    @Override
    public void buildStage() {
        Constant.setUpBlockConstants((int) getWidth(), (int) getHeight());
        new ActorInitializer();
    }

    public static void loadBoard(Board board) {
        new MapManager(new EditorMap());
        MapManager.setBoard(board);
    }
}
