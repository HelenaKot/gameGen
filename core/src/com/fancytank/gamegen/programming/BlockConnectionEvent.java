package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.ConnectionArea;

public class BlockConnectionEvent {
    ConnectionArea baseConnector, attachingConnector;

    public BlockConnectionEvent(ConnectionArea baseConnector, ConnectionArea attachingConnector) {
        this.baseConnector = baseConnector;
        this.attachingConnector = attachingConnector;
    }

    float getConnectedComponentHeight() {
        return attachingConnector.coreBlock.getHeight();
    }
}
