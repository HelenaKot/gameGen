package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.looks.BlockShape;

public class BlockData {
    public int significance;

    BlockData parent;
    BlockData descendant;
    InputFragment[] inputs;
    public BlockShape shape;

    public BlockData(InputFragment[] inputs) {
        this.shape = BlockShape.ENCLOSED;
        this.inputs = inputs;
    }

    public BlockData(InputFragment[] inputs, BlockShape shape) {
        this.shape = shape;
        this.inputs = inputs;
    }

    public void setParent(BlockData parent) { this.parent = parent; }

    public void  setDescendant(BlockData descendant) { this.descendant = descendant; }

    public InputFragment[] getInputs() { return inputs; }
}
