package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;

public class EditorActor extends Actor {
    int x, y;
    String className;
    Color tint;
    Texture texture;

    public EditorActor(int x, int y) {
        this.x = x;
        this.y = y;
        tint = Color.WHITE;
        texture = new Texture(Gdx.files.internal("block_star_cutout.png"));
        addListener(classSwapListener());
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        batch.draw(texture, this.getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
    }

    private InputListener classSwapListener() {
        return new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //TODO
                return true;
            }
        };
    }
}
