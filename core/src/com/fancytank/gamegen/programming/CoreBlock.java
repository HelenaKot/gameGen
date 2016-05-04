package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CoreBlock extends Actor {
    Color tint;
    BlockShape shape;
    BlockAppearance blockAppearance;

    public CoreBlock(BlockShape shape, Color tint) {
        this.tint = tint;
        this.shape = shape;
        blockAppearance = new BlockAppearance(this, "le Placeholder");
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

    Rectangle getBoundingBox() {
        Vector2 pos = localToStageCoordinates(new Vector2(getX(), getY()));
        return new Rectangle(pos.x, pos.y, getWidth(), getHeight());
    }
}
