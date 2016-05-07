package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.looks.InputType;

public class BlockShape {
    boolean[] connects = new boolean[4];
    InputType[] inputs;

    public static BlockShape
            VARIABLE = new BlockShape(new boolean[]{false, false, false, true}),
            CHAIN_FUNCTION = new BlockShape(new boolean[]{true, true, true, false},
                    new InputType[]{InputType.VARIABLE});

    BlockShape(boolean[] connects) {
        this.connects = connects;
        this.inputs = new InputType[]{InputType.DUMMY};
    }

    BlockShape(boolean[] connects, InputType[] inputs) {
        this.connects = connects;
        this.inputs = inputs;
    }

    public boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
