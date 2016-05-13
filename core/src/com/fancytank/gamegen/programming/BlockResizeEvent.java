package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.ConnectionArea;

public class BlockResizeEvent extends BlockConnectionEvent {

    public BlockResizeEvent(ConnectionArea baseConnector, ConnectionArea attachingConnector, boolean isConnecting) {
        super(baseConnector, attachingConnector, isConnecting);
    }

    public float getConnectedComponentHeight() {
        return attachingConnector.coreBlock.getHeight();
    }

    public BlockAppearance getBaseBlockAppearance() {
        return baseConnector.coreBlock.blockAppearance;
    }
}
