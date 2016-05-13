package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.looks.ConnectionArea;

public class BlockConnectionEvent {
    public final ConnectionArea baseConnector, attachingConnector;
    boolean isConnecting;

    public BlockConnectionEvent(ConnectionArea baseConnector, ConnectionArea attachingConnector, boolean isConnecting) {
        this.baseConnector = baseConnector;
        this.attachingConnector = attachingConnector;
        this.isConnecting = isConnecting;
    }

    public boolean isConnecting() {
        return isConnecting;
    }
}
