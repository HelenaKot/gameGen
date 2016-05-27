package com.fancytank.gamegen;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.game.script.ScriptLoader;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import org.greenrobot.eventbus.EventBus;

public class GameScreen extends AbstractScreen {
    static GameScreen instance;
    private float width, height;

    @Override
    public void buildStage() {
        width = getWidth();
        height = getHeight();
        instance = this;
        EventBus.getDefault().post(MainGdx.AppStatus.SETUP_FINISHED);
    }

    public static void loadGame(ProgrammingBlockSavedInstance[] data) {
        Constant.setUpBlockConstants((int) instance.width, 10);
        ScriptLoader.load(data);
        new MapManager(instance.width, instance.height);
    }
}
