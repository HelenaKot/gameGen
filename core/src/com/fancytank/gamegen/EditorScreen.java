package com.fancytank.gamegen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.editor.BlockButton;
import com.fancytank.gamegen.editor.EditorBackground;
import com.fancytank.gamegen.editor.TrashCan;
import com.fancytank.gamegen.programming.Workspace;
import com.fancytank.gamegen.programming.blocks.BlockCreateEvent;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EditorScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        setUp();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(MainGdx.AppStatus.SETUP_FINISHED);
    }

    private void setUp() {
        new PatchTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        BlockAppearance.loadFont(new BitmapFont(Gdx.files.internal("fontvarsmall.fnt"), Gdx.files.internal("fontvarsmall.png"), false));
        addActor(new EditorBackground(getWidth(), getHeight()));
        addActor(new TrashCan(getWidth()));
        addActor(new BlockButton(getHeight()));
    }

    @Subscribe
    public void onEvent(BlockCreateEvent event) {
        ProgrammingBlock template = new ProgrammingBlock(event.blockActorPattern.getBlockData(), event.blockActorPattern.getColor());
        Actor newBlock = Workspace.clone(template);
        newBlock.setPosition(getWidth() * 0.4f, getHeight() / 2);
        addActor(newBlock);
        template.destroy();
    }
}
