package com.fancytank.generated.game.programming;

public class BlockShape {
    boolean[] connects = new boolean[4];

    public static BlockShape
            VARIABLE = new BlockShape(new boolean[]{false, false, false, true});

    BlockShape(boolean[] connects) {
        this.connects = connects;
    }
}
