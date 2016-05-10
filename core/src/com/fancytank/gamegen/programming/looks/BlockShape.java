package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.Direction;

public class BlockShape {
    public int significance;
    boolean[] connects = new boolean[3];

    public static BlockShape
            ENCLOSED = new BlockShape(10, new boolean[]{false, false, false}),
            CHAIN_FUNCTION = new BlockShape(5, new boolean[]{true, false, true}),
            VARIABLE = new BlockShape(0, new boolean[]{false, true, false});


    BlockShape(int significance, boolean[] connects) {
        this.significance = significance;
        this.connects = connects;
    }

    public boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
