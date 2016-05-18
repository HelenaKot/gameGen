package com.fancytank.gamegen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fancytank.gamegen.editor.EditorBackground;
import com.fancytank.gamegen.programming.ProgrammingBlock;
import com.fancytank.gamegen.programming.blocks.BlockCreateEvent;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AndroidGameGenerator extends ApplicationAdapter {
    static private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setUp();
        EventBus.getDefault().post(new SetUpFinished());
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(BlockCreateEvent event) {
        new ProgrammingBlock(event.blockActorPattern.getBlockData(), event.blockActorPattern.getColor()).setPosition(stage.getWidth()*0.4f, stage.getHeight()/2);
    }

    private void setUp() {
        new PatchTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        BlockAppearance.loadFont(new BitmapFont(Gdx.files.internal("fontvarsmall.fnt"), Gdx.files.internal("fontvarsmall.png"), false));
        EditorBackground bg = new EditorBackground(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);
    }

    static public void addToStage(Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    class SetUpFinished {}
}
