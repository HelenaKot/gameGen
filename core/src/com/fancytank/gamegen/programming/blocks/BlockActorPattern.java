package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.ValueType;

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

    public BlockActorPattern setValue(String value, ValueType valueType) {
        blockData.setValue(value, valueType);
        blockData.getInputs()[0].labelText = value;
        return this;
    }

    public BlockActorPattern setLabel(String text) {
        blockData.getInputs()[0].labelText = text;
        return this;
    }


    public void spawn() {
        BlockCreateEvent bce = new BlockCreateEvent(this);
        EventBus.getDefault().post(bce);
    }
}
