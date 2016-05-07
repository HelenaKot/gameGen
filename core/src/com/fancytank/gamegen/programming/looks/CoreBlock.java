package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.data.BlockData;

public class CoreBlock extends Actor {
    Color tint;
    BlockData data;
    BlockAppearance blockAppearance;

    public CoreBlock(BlockData data, Color tint) {
        this.tint = tint;
        this.data = data;
        blockAppearance = new com.fancytank.gamegen.programming.looks.BlockAppearance(this, "le Placeholder\nlorem ipsum\ndolor sit amet");
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        blockAppearance.drawShape(batch, alpha);
    }

    @Override
    public float getWidth() { return blockAppearance.getWidth(); }

    @Override
    public float getHeight() { return blockAppearance.getHeight(); }

    public Rectangle getBoundingBox() {
        Vector2 pos = localToStageCoordinates(new Vector2(getX(), getY()));
        return new Rectangle(pos.x, pos.y, getWidth(), getHeight());
    }
}
