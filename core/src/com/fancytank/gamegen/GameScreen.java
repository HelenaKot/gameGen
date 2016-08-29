package com.fancytank.gamegen;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.game.map.GameMap;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.game.script.ScriptLoader;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GameScreen extends AbstractScreen {
    private MapManager mapManager;

    @Override
    public void buildStage() {
        super.buildStage();
        Constant.setUpBlockConstants((int) getWidth(), (int) getHeight());
        mapManager = new MapManager(new GameMap());
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(ProgrammingBlockSavedInstance[] data) {
        new ActorInitializer();
        ScriptLoader.load(data);
        mapManager.setBoard(BoardManager.get("default"));
    }

    @Override
    public void dispose() {
        super.dispose();
        EventBus.getDefault().unregister(this);
    }

}
