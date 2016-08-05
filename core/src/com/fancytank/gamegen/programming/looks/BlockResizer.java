package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.BlockResizeEvent;

import static com.fancytank.gamegen.programming.looks.Constant.padding;

public final class BlockResizer {

    public static void resizeBlock(BlockResizeEvent event) {
        CoreBlock coreBlock = event.baseConnector.coreBlock;
        float height = padding * 2 + event.getConnectedComponentHeight();
        float heightDelta = event.baseConnector.blockInputAppearance.getHeight() - height;

        event.baseConnector.blockInputAppearance.setHeight(height);
        coreBlock.blockAppearance.updateSize();
        coreBlock.programmingBlock.setBounds(coreBlock.programmingBlock.getX(), coreBlock.programmingBlock.getY(), coreBlock.getWidth(), coreBlock.getHeight());

        if (coreBlock.data.hasParent())
            ConnectionPlacer.updateConnectors(coreBlock.data.getParent().getCoreBlock());
        else
            coreBlock.programmingBlock.setPosition(coreBlock.programmingBlock.getX(), coreBlock.programmingBlock.getY() + heightDelta);
        ConnectionPlacer.updateConnectors(coreBlock);
    }
}
