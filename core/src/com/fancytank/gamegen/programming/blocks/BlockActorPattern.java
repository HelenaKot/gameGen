package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;

import org.greenrobot.eventbus.EventBus;

public class BlockActorPattern {
    String name;
    BlockData blockData;
    Color tint;

    public BlockActorPattern(String name, BlockData blockData, Color tint) {
        this.name = name;
        this.blockData = blockData;
        this.tint = tint;
    }

    public String getName() {
        return name;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public Color getColor() {
        return tint;
    }

    public void setValue(String value) {
        blockData.setValue(value);
        blockData.getInputs()[0].labelText = value;
    }

    public void spawn() {
        BlockCreateEvent bce = new BlockCreateEvent(this);
        EventBus.getDefault().post(bce);
    }
}
