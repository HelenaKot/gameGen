package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GenericActor extends BaseActor {
    Texture texture;
    Color tint;

    public GenericActor(Color tint, int x, int y) {
        super(x, y);
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        this.tint = tint;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }
}
