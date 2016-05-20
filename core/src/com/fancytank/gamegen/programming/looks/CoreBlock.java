package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;

public class CoreBlock extends Actor {
    public Color tint;
    ProgrammingBlock programmingBlock;
    public BlockData data;
    public BlockAppearance blockAppearance;

    public CoreBlock(ProgrammingBlock programmingBlock, BlockData data, Color tint) {
        this.programmingBlock = programmingBlock;
        this.tint = tint;
        this.data = data;
        blockAppearance = new BlockAppearance(this);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        blockAppearance.drawShape(batch, alpha);
    }

    @Override
    public float getWidth() {
        return blockAppearance.getWidth();
    }

    @Override
    public float getHeight() {
        return blockAppearance.getHeight();
    }

    public ProgrammingBlock getProgrammingBlock() {
        return programmingBlock;
    }

    public Rectangle getBoundingBox() {
        Vector2 pos = localToStageCoordinates(new Vector2(getX(), getY()));
        return new Rectangle(pos.x, pos.y, getWidth(), getHeight());
    }

    public InputFragment[] getInputs() {
        return data.getInputs();
    }
}
