package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.looks.Utility;

public class TrashCan extends Actor {
    public static TrashCan instance;
    private Texture binClosed, binOpen, binCurrent;
    private float width = 256, height = 256;
    private boolean hover;

    public TrashCan(float mapWidth) {
        binClosed = new Texture(Gdx.files.internal("garbage.png"));
        binOpen = new Texture(Gdx.files.internal("garbage_open.png"));
        binCurrent = binClosed;
        hover = false;
        instance = this;
        setPosition(mapWidth - width, 0);
    }

    public Rectangle getBoundingBox() {
        Vector2 pos = Utility.myLocalToStageCoordinates(this);
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public boolean setHover(boolean hover) {
        if (hover != this.hover) {
            if (hover)
                binCurrent = binOpen;
            else
                binCurrent = binClosed;
            this.hover = hover;
        }
        return hover;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.GRAY);
        batch.draw(binCurrent, getX(), getY(), width, height);
    }
}
