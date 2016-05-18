package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;

public class CustomBlockPattern implements BlockActorPattern{
    String name;
    BlockData blockData;
    Color tint;

    public CustomBlockPattern(String name, BlockData blockData, Color tint) {
        this.name = name;
        this.blockData = blockData;
        this.tint = tint;
    }

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
}
