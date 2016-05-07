package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.InputType;

public class BlockArrangement {
    boolean[] connects = new boolean[4];
    InputType[] inputs;

    public static BlockArrangement
            VARIABLE = new BlockArrangement(new boolean[]{false, false, false, true}),
            CHAIN_FUNCTION = new BlockArrangement(new boolean[]{true, true, true, false},
                    new InputType[]{InputType.VARIABLE});

    BlockArrangement(boolean[] connects) {
        this.connects = connects;
        this.inputs = new InputType[]{InputType.DUMMY};
    }

    BlockArrangement(boolean[] connects, InputType[] inputs) {
        this.connects = connects;
        this.inputs = inputs;
    }

    boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
