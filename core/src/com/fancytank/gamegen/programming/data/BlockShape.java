package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.Direction;

import java.io.Serializable;

public enum BlockShape implements Serializable {
    TIMER(10, new boolean[]{false, false, false}),
    ACTION_LISTENER(10, new boolean[]{false, false, false}),
    CHAIN_METHOD(5, new boolean[]{true, false, true}),
    LAST_METHOD(5, new boolean[]{true, false, false}),
    VARIABLE_DECLARATION(10, new boolean[]{false, false, false}),
    VARIABLE(0, new boolean[]{false, true, false});

    private static final long serialVersionUID = 1233613063064496933L;
    public int significance;
    boolean[] connects = new boolean[3];

    BlockShape(int significance, boolean[] connects) {
        this.significance = significance;
        this.connects = connects;
    }

    public boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }

    public boolean enclosed() {
        for (boolean connection : this.connects)
            if (connection)
                return false;
        return true;
    }
}
