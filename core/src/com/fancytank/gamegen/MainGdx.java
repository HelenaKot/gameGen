package com.fancytank.gamegen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainGdx extends Game {
    public enum AppStatus {GDX_INIT_FINISHED, SETUP_FINISHED}

    @Override
    public void create() {
        loadAssets();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(AppStatus.GDX_INIT_FINISHED);
    }

    private void loadAssets() {
        new PatchTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        BlockAppearance.loadFont(new BitmapFont(Gdx.files.internal("fontvarsmall.fnt"), Gdx.files.internal("fontvarsmall.png"), false));
        TileType.initTextures();
    }

    @Subscribe
    public void onEvent(ScreenEnum screen) {
        Gdx.app.postRunnable(() -> {
            AbstractScreen newScreen = screen.getScreen();
            newScreen.buildStage();
            EventBus.getDefault().post(MainGdx.AppStatus.SETUP_FINISHED);
            setScreen(newScreen);
        });
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