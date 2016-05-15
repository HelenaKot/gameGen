package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.looks.BlockShape;
import com.fancytank.gamegen.programming.looks.CoreBlock;

public class BlockData {
    public BlockShape shape;
    BlockData parent;
    BlockData descendant;
    InputFragment[] inputs;
    CoreBlock coreBlock;

    public BlockData(InputFragment[] inputs) {
        this.shape = BlockShape.ENCLOSED;
        this.inputs = inputs;
    }

    public BlockData(InputFragment[] inputs, BlockShape shape) {
        this.shape = shape;
        this.inputs = inputs;
    }

    public void setParent(BlockData parent) {
        this.parent = parent;
        parent.descendant = this;
    }

    public void removeParent() {
        parent.descendant = null;
        parent = null;
    }

    public BlockData getDescendant() {
        return descendant;
    }

    public boolean hasDescendant() {
        return descendant != null;
    }

    public void setCoreBlock (CoreBlock coreBlock) {
        this.coreBlock = coreBlock;
    }

    public CoreBlock getCoreBlock () {
        return coreBlock;
    }

    public InputFragment[] getInputs() {
        return inputs;
    }
}
