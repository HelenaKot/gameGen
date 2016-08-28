package com.fancytank.gamegen;

import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.game.map.MapManager;

public class DesignScreen extends AbstractScreen {
    private String currentBoard = "default";
    private MapManager mapManager;

    @Override
    public void buildStage() {
        super.buildStage();
        Constant.setUpBlockConstants((int) getWidth(), (int) getHeight());
        mapManager = new MapManager(new EditorMap());
        mapManager.setBoard(BoardManager.get("default"));
    }

    @Override
    void saveState() {
        BoardManager.addBoard(currentBoard, ((EditorMap) mapManager.gameMap).getMapAsBoard());
    }
}
