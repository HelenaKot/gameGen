package com.fancytank.gamegen.programming;

public class BlockShape {
    boolean[] connects = new boolean[4];
    //TODO : centerpiece
    // todo multiple inputs
    // todo function input
    // todo textfields
    int inputs = 0;

    public static BlockShape
            VARIABLE = new BlockShape(new boolean[]{false, false, false, true}),
            CHAIN_FUNCTION = new BlockShape(new boolean[]{true, true, true, false}, 1) ;

    BlockShape(boolean[] connects) {
        this.connects = connects;
    }

    BlockShape(boolean[] connects, int inputs) {
        this.inputs = inputs;
        this.connects = connects;
    }

    boolean connects(Direction direction) {
        return connects[direction.ordinal()];
    }
}
