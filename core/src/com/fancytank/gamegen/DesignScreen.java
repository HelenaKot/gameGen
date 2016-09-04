package com.fancytank.gamegen;

import com.fancytank.gamegen.editor.EditorMap;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.game.script.ScriptLoader;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DesignScreen extends AbstractScreen {
    private String currentBoard = "default";
    private MapManager mapManager;

    @Override
    public void buildStage() {
        super.buildStage();
        Constant.setUpBlockConstants((int) getWidth(), (int) getHeight());
        mapManager = new MapManager(new EditorMap());
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(Board data) {
        mapManager.setBoard(data);
    }

    @Override
    void saveState() {
        BoardManager.addBoard(currentBoard, ((EditorMap) mapManager.gameMap).getMapAsBoard());
    }

    @Override
    public void dispose() {
        super.dispose();
        EventBus.getDefault().unregister(this);
    }
}
