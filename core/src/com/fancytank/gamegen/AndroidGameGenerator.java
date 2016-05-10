package com.fancytank.gamegen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fancytank.gamegen.editor.EditorBackground;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.looks.InputType;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;
import com.fancytank.gamegen.programming.ProgrammingBlock;

public class AndroidGameGenerator extends ApplicationAdapter {
    static private Stage stage;
    //OrthographicCamera cam = new OrthographicCamera();

    @Override
    public void create() {
        stage = new Stage();
        //new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));
        Gdx.input.setInputProcessor(stage);

        setUp();
    }

    private void setUp() {
        new PatchTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        BlockAppearance.loadFont(new BitmapFont(Gdx.files.internal("fontvarsmall.fnt"), Gdx.files.internal("fontvarsmall.png"), false));
        EditorBackground bg = new EditorBackground(stage.getWidth(), stage.getHeight());
        stage.addActor(bg);

       // new ProgrammingBlock(new BlockData(new InputFragment[] { new InputFragment(InputType.DUMMY, "DERP")}), Color.ORANGE);
        new ProgrammingBlock(new BlockData(new InputFragment[] { new InputFragment(InputType.VARIABLE, "derp herp"), new InputFragment(InputType.SOCKET, "HERP"),
                new InputFragment(InputType.VARIABLE, "derp"),  new InputFragment(InputType.SOCKET, "derp"),  new InputFragment(InputType.DUMMY, "herp")}), Color.ORANGE);
       // new ProgrammingBlock(new BlockData(new InputFragment[] { new InputFragment(InputType.SOCKET, "NOPE")}), Color.ORANGE);
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
}