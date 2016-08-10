package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.fancytank.gamegen.game.Constant;

public class GenericActor extends BaseActor {
    public Texture texture = new Texture(Gdx.files.internal("block_bounds_full.png"));

    public GenericActor(int x, int y, String name) {
        super(x, y, name);
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        batch.draw(texture, this.getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
    }

    @Override
    public String toString() {
        return "Generic Actor at " + x + " " + y;
    }
}
