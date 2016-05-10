package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.Direction;

public class BlockShape {
    boolean[] connects = new boolean[4];

    public static BlockShape
            ENCLOSED = new BlockShape(new boolean[] {false, false, false, false}),
            VARIABLE = new BlockShape(new boolean[]{false, false, false, true}),
            CHAIN_FUNCTION = new BlockShape(new boolean[]{true, true, true, false});

    BlockShape(boolean[] connects) {
        this.connects = connects;
    }

    public boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
