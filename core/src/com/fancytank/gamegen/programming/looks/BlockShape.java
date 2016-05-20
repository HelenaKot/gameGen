package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.Direction;

import java.io.Serializable;

public enum BlockShape implements Serializable {
    ENCLOSED(10, new boolean[]{false, false, false}),
    CHAIN_FUNCTION(5, new boolean[]{true, false, true}),
    VARIABLE(0, new boolean[]{false, true, false});

    public int significance;
    boolean[] connects = new boolean[3];

    BlockShape(int significance, boolean[] connects) {
        this.significance = significance;
        this.connects = connects;
    }

    public boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
