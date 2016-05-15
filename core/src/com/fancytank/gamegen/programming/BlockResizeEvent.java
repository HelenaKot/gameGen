package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class BlockResizeEvent extends BlockConnectionEvent {

    public BlockResizeEvent(ConnectionArea baseConnector, ConnectionArea attachingConnector, boolean isConnecting) {
        super(baseConnector, attachingConnector, isConnecting);
    }

    public float getConnectedComponentHeight() {
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
