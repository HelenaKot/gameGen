package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;

import org.greenrobot.eventbus.EventBus;

public class CustomBlockPattern implements BlockActorPattern{
    String name;
    BlockData blockData;
    Color tint;

    public CustomBlockPattern(String name, BlockData blockData, Color tint) {
        this.name = name;
        this.blockData = blockData;
        this.tint = tint;
    }

    //todo refactoring - merge with blockActorPattern
    @Override
    public String getName() {
        return name;
    }

    @Override
    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public Color getColor() {
        return tint;
    }

    @Override
    public void setValue(String value) {
        blockData.setValue(value);
        blockData.getInputs()[0].labelText = value;
    }

    @Override
    public void spawn() {
        BlockCreateEvent bce = new BlockCreateEvent(this);
        EventBus.getDefault().post(bce);
    }
}
