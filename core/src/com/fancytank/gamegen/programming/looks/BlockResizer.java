package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.BlockResizeEvent;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class BlockResizer {

    public static void resizeBlock(BlockResizeEvent event) {
        CoreBlock coreBlock = event.baseConnector.coreBlock;
        float height = padding * 2;
        if (event.isConnecting())
            height += event.getConnectedComponentHeight();
        float heightDelta = event.baseConnector.blockInputAppearance.getHeight() - height;

        event.baseConnector.blockInputAppearance.setHeight(height);
        coreBlock.blockAppearance.updateSize();

        coreBlock.parent.setBounds(coreBlock.parent.getX(), coreBlock.parent.getY(), coreBlock.getWidth(), coreBlock.getHeight());
        coreBlock.parent.setPosition(coreBlock.parent.getX(), coreBlock.parent.getY() + heightDelta);

        ConnectionPlacer.updateConnectors(coreBlock);
    }
}
