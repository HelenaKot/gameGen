package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlockActor extends Actor {
    Color tint;
    BlockShape shape;
    BlockAppearance blockAppearance;
    private Rectangle boundingBox;

    public BlockActor(BlockShape shape, Color tint) {
        this.tint = tint;
        this.shape = shape;
        blockAppearance = new BlockAppearance(this, "le Placeholder");
        boundingBox = new Rectangle((int) getX(), (int) getY(), (int) blockAppearance.getWidth(), (int) blockAppearance.getHeight());
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        boundingBox.setPosition(boundingBox.getX() + deltaX, boundingBox.getY() + deltaY);
        blockAppearance.translate(deltaX, deltaY);
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
        return boundingBox;
    }
}
