package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import org.greenrobot.eventbus.EventBus;

public class BlockButton extends Actor {
    public static BlockButton instance;
    private Texture newBlock;
    private float width = 256, height = 256;

    public BlockButton(float mapHeight) {
        newBlock = new Texture(Gdx.files.internal("new_block.png"));
        setPosition(0, mapHeight - height);
        setBounds(getX(), getY(), width, height);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                EventBus.getDefault().post(new OpenLayer());
                return true;
            }
        });
        instance = this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.GRAY);
        batch.draw(newBlock, getX(), getY());
    }

    public class OpenLayer {
    }
}
