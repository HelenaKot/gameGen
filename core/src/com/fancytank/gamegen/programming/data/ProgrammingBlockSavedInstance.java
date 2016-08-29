package com.fancytank.gamegen.programming.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.Utility;

import java.io.Serializable;

public class ProgrammingBlockSavedInstance implements Serializable {
    private static final long serialVersionUID = 1233613063064496931L;
    public BlockData data;
    int color;
    float posX, posY;

    public ProgrammingBlockSavedInstance(ProgrammingBlock programmingBlock) {
        data = programmingBlock.coreBlock.data;
        color = Color.rgba8888(programmingBlock.coreBlock.tint);
        Vector2 pos = Utility.myLocalToStageCoordinates(programmingBlock);
        posX = pos.x + programmingBlock.getWidth();
        posY = pos.y + programmingBlock.getHeight();
    }

    public ProgrammingBlock restore() {
        ProgrammingBlock restoredBlock = new ProgrammingBlock(data, new Color(color));
        restoredBlock.setPosition(posX - restoredBlock.coreBlock.getWidth(), posY - restoredBlock.coreBlock.getHeight());
        return restoredBlock;
    }
}