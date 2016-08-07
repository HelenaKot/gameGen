package com.fancytank.gamegen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainGdx extends Game {
    public enum AppStatus {GDX_INIT_FINISHED, SETUP_FINISHED}

    public static ScreenEnum currentScreen;
    private static ScreenEnum startingScreen = ScreenEnum.EDITOR_SCREEN;

    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        EventBus.getDefault().register(this);
        currentScreen = startingScreen;
        EventBus.getDefault().post(AppStatus.GDX_INIT_FINISHED);
    }

    @Subscribe
    public void onEvent(ScreenEnum screen) {
        setScreen(currentScreen = screen);
    }

    private void setScreen(final ScreenEnum screen) {
        Gdx.app.postRunnable(() -> ScreenManager.getInstance().showScreen(screen));
    }

    static public void addToStage(Actor actor) {
        ScreenEnum.screenInstance.addToStage(actor);
    }

    static public Stage getStage() {
        return ScreenEnum.screenInstance;
    }

    @Override
    public void dispose() {
        super.dispose();
        EventBus.getDefault().unregister(this);
    }
}
