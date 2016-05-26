package com.fancytank.gamegen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainGdx extends Game {
    public enum AppStatus { SETUP_FINISHED, EDITOR_SCREEN, TEST_SCREEN}

    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        if (ScreenEnum.screenType != null)
                ScreenManager.getInstance().showScreen(ScreenEnum.screenType);
        EventBus.getDefault().register(this);
        setScreen(ScreenEnum.GAME_SCREEN);
    }

    //todo this is not working v
    @Subscribe
    public void onEvent(MainGdx.AppStatus status) {
        if (status == AppStatus.EDITOR_SCREEN)
            setScreen(ScreenEnum.EDITOR_SCREEN);
        else if (status == AppStatus.TEST_SCREEN)
            setScreen(ScreenEnum.GAME_SCREEN);
    }

    private void setScreen(final ScreenEnum screen) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                ScreenManager.getInstance().showScreen(screen);
            }
        });
    }

    static public void addToStage(Actor actor) {
        ScreenEnum.screenInstance.addActor(actor);
    }

    static public Stage getStage() {
        return ScreenEnum.screenInstance;
    }

}
