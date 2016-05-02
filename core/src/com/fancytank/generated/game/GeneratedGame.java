package com.fancytank.generated.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.fancytank.generated.game.programming.Block;
import com.fancytank.generated.game.programming.BlockShape;
import com.fancytank.generated.game.programming.BlockTextureManager;
import com.fancytank.generated.game.programming.ProgrammingBlock;

public class GeneratedGame extends ApplicationAdapter {
    private Stage stage;
    private Color bgColor = Color.FOREST;
    OrthographicCamera cam = new OrthographicCamera();

    @Override
    public void create() {
        stage = new Stage(new ScalingViewport(Scaling.fillY, 400, 400, cam));
        //new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));
        Gdx.input.setInputProcessor(stage);

        setUp();
    }

    private void setUp() {
        new BlockTextureManager(new TextureAtlas(Gdx.files.internal("blocks.atlas")));
        ProgrammingBlock.loadSkin(new Skin(Gdx.files.internal("uiskin.json")));
        addToStage(new Block(BlockShape.VARIABLE, new Skin(Gdx.files.internal("uiskin.json"))));
    }

    public void addToStage(Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

//        drawTriangle(50, 50, 70, 40, Color.BLUE);
//        drawTriangle(10, 10, 40, 40, Color.RED);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
