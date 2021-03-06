package com.fancytank.gamegen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fancytank.gamegen.game.actor.ActorInitializer;

public abstract class AbstractScreen extends Stage implements Screen {
    public void buildStage() {
        new ActorInitializer();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.act(delta);
        super.draw();
    }

    void saveState() {
    }

    public void addToStage(Actor actor) {
        addActor(actor);
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        saveState();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        saveState();
    }

    @Override
    public void dispose() {

    }
}
