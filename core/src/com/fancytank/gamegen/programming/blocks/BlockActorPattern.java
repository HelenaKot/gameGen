package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;

public interface BlockActorPattern {
    String getName();
    BlockData getBlockData();
    Color getColor();
    void setValue(String value);
    void spawn();
}
