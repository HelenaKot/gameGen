package com.fancytank.gamegen;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.GameMap;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.game.script.ScriptLoader;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import org.greenrobot.eventbus.EventBus;

public class GameScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        Constant.setUpBlockConstants((int) getWidth(), (int) getHeight());
    }

    public static void loadGame(ProgrammingBlockSavedInstance[] data, Board board) {
        new ActorInitializer();
        ScriptLoader.load(data);
        new MapManager(new GameMap());
        MapManager.setBoard(board);
    }

    @Override
    public void dispose() {
        super.dispose();
        EventBus.getDefault().unregister(this);
    }
}
