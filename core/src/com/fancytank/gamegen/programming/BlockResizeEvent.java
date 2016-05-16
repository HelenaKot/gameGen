package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class BlockResizeEvent extends BlockConnectionEvent {
    boolean isLast = false;

    public BlockResizeEvent(ConnectionArea baseConnector, ConnectionArea attachingConnector, boolean isConnecting) {
        super(baseConnector, attachingConnector, isConnecting);
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    public float getConnectedComponentHeight() {
        if (isLast && !isConnecting)
            return 0;
        return sumHeight(attachingConnector.coreBlock, 0);
    }

    private float sumHeight(CoreBlock block, float height) {
        height += block.getHeight() - padding;
        if (block.data.hasDescendant()) {
            return sumHeight(block.data.getDescendant().getCoreBlock(), height);
        }
        return height;
    }

    public BlockAppearance getBaseBlockAppearance() {
        return baseConnector.coreBlock.blockAppearance;
    }
}
