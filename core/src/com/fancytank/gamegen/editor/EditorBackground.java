package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class EditorBackground extends Actor {
    Texture background;
    private int width, height;

    public EditorBackground(float width, float height) {
        setBounds(0, 0, this.width = (int) width, this.height = (int) height);
        background = new Texture(Gdx.files.internal("background.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public void setDraggable(Actor actor) {
        addCaptureListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                actor.moveBy(deltaX, deltaY);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.GRAY);
        batch.draw(background, 0, 0, 0, 0, width, height);
    }
}