package com.fancytank.gamegen.programming.data;

public class BlockData {
    public int significance;

    BlockData parent;
    BlockData descendant;
    InputFragment[] inputs;

    BlockData(InputFragment[] inputs) {
        this.inputs = inputs;
    }

    public void setParent(BlockData parent) { this.parent = parent; }

    public void  setDescendant(BlockData descendant) { this.descendant = descendant; }
}
