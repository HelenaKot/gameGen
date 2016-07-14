package com.fancytank.gamegen.programming.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.Utility;

import java.io.Serializable;

public class ProgrammingBlockSavedInstance implements Serializable {
    private static final long serialVersionUID = 1233613063064496931L;
    public BlockData data;
    float a, r, g, b;
    float posX, posY;

    public ProgrammingBlockSavedInstance(ProgrammingBlock programmingBlock) {
        data = programmingBlock.coreBlock.data;
        a = programmingBlock.coreBlock.tint.a;
        r = programmingBlock.coreBlock.tint.r;
        g = programmingBlock.coreBlock.tint.g;
        b = programmingBlock.coreBlock.tint.b;
        Vector2 pos = Utility.myLocalToStageCoordinates(programmingBlock);
        posX = pos.x + programmingBlock.getWidth();
        System.out.println("pos" + pos.x + " " + programmingBlock.coreBlock.getWidth());
        posY = pos.y + programmingBlock.getHeight();
        System.out.println("pos" + pos.y + " " + programmingBlock.coreBlock.getHeight());
    }

    public ProgrammingBlock restore() {
        ProgrammingBlock restoredBlock = new ProgrammingBlock(data, new Color(r, g, b, a));
        restoredBlock.setPosition(posX - restoredBlock.coreBlock.getWidth(), posY - restoredBlock.coreBlock.getHeight());
        System.out.println("pos" +posX + " " + restoredBlock.coreBlock.getWidth());
        System.out.println("pos" +posY + " " + restoredBlock.coreBlock.getHeight());
        return restoredBlock;
    }
}