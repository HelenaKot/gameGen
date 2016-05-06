package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EditorBackground extends Actor {
    Texture background;
    private int width, height;

    public EditorBackground(float width, float height) {
        this.width = (int) width;
        this.height = (int) height;
        background = new Texture(Gdx.files.internal("background.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.GRAY);
        batch.draw(background, 0, 0, 0, 0, width, height);
    }
}