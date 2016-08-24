package com.fancytank.gamegen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fancytank.gamegen.programming.BlockButton;
import com.fancytank.gamegen.programming.EditorBackground;
import com.fancytank.gamegen.programming.TrashCan;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.Workspace;
import com.fancytank.gamegen.programming.blocks.BlockCreateEvent;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EditorScreen extends AbstractScreen {
    private Group programmingBlocks;
    private EditorBackground editorBackground;

    EditorScreen() {
        programmingBlocks = new Group();
    }

    @Override
    public void buildStage() {
        setUp();
        editorBackground.setDraggable(programmingBlocks);
        EventBus.getDefault().register(this);
    }

    private void setUp() {
        new PatchTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        BlockAppearance.loadFont(new BitmapFont(Gdx.files.internal("fontvarsmall.fnt"), Gdx.files.internal("fontvarsmall.png"), false));
        new ActorInitializer();
        addActor(editorBackground = new EditorBackground(getWidth(), getHeight()));
        addActor(new TrashCan(getWidth()));
        addActor(new BlockButton(getHeight()));
        addActor(programmingBlocks);
    }

    @Subscribe
    public void onEvent(BlockCreateEvent event) {
        ProgrammingBlock template = new ProgrammingBlock(event.blockActorPattern.getBlockData(), event.blockActorPattern.getColor());
        Actor newBlock = Workspace.clone(template);
        newBlock.setPosition(getWidth() * 0.4f, getHeight() / 2);
        addToStage(newBlock);
        template.destroy();
    }

    @Override
    public void dispose() {
        super.dispose();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addToStage(Actor actor) {
        actor.moveBy(-programmingBlocks.getX(), -programmingBlocks.getY()); // compensating for pan movement
        programmingBlocks.addActor(actor);
    }
}
