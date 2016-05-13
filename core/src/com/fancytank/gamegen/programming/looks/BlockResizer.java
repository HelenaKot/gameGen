package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.BlockResizeEvent;

public class BlockResizer {

    public static void resizeBlock(BlockResizeEvent event) {
        CoreBlock coreBlock = event.baseConnector.coreBlock;

        event.baseConnector.blockInputAppearance.setHeight(event.getConnectedComponentHeight());
        coreBlock.blockAppearance.updateSize();
        ConnectionPlacer.updateConnectors(coreBlock);

        float heightDelta = coreBlock.getHeight() - coreBlock.parent.getHeight();
        coreBlock.parent.setBounds(coreBlock.parent.getX(), coreBlock.parent.getY(), coreBlock.getWidth(), coreBlock.getHeight());
    }
}
