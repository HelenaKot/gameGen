package com.fancytank.gamegen.programming.data;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;

import java.io.Serializable;

public class ProgrammingBlockSavedInstance implements Serializable {
    BlockData data;
    float a, r, g, b;
    float posX, posY;

    public ProgrammingBlockSavedInstance(ProgrammingBlock programmingBlock) {
        data = programmingBlock.coreBlock.data;
        a = programmingBlock.coreBlock.tint.a;
        r = programmingBlock.coreBlock.tint.r;
        g = programmingBlock.coreBlock.tint.g;
        b = programmingBlock.coreBlock.tint.b;
        posX = programmingBlock.getX();
        posY = programmingBlock.getY();
    }

    public ProgrammingBlock restore() {
        ProgrammingBlock restoredBlock = new ProgrammingBlock(data, new Color(r, g, b, a));
        restoredBlock.setPosition(posX, posY);
        return restoredBlock;
    }
}