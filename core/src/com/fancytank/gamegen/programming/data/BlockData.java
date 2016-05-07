package com.fancytank.gamegen.programming.data;

public class BlockData {
    public int significance;

    BlockData parent;
    BlockData descendant;
    InputFragment[] inputs;
    public com.fancytank.gamegen.programming.looks.BlockShape shape;

    public BlockData(InputFragment[] inputs) {
        this.shape = com.fancytank.gamegen.programming.looks.BlockShape.ENCLOSED;
        this.inputs = inputs;
    }

    BlockData(com.fancytank.gamegen.programming.looks.BlockShape shape, InputFragment[] inputs) {
        this.shape = shape;
        this.inputs = inputs;
    }

    public void setParent(BlockData parent) { this.parent = parent; }

    public void  setDescendant(BlockData descendant) { this.descendant = descendant; }
}
