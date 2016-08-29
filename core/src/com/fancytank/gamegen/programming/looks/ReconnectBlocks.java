package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.blocks.BlockManager;
import com.fancytank.gamegen.programming.blocks.ConnectionRules;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;

import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public class ReconnectBlocks {
    public static class ReconnectEvent {}

    public static void reconnectAll() {
        for (ProgrammingBlock programmingBlock : BlockManager.getBlocList())
            if (programmingBlock.coreBlock.data.shape.enclosed())
                reconnectBlock(programmingBlock.coreBlock.data);
    }

    private static void reconnectBlock(BlockData data) {
        for (InputFragment inputFragment : data.getInputs())
            if (inputFragment.connectedTo != null)
                reconnectInput(inputFragment);
        if (data.hasDescendant())
            reconnectDescendant(data);
    }

    private static void reconnectInput(InputFragment inputFragment) {
        ConnectionRules.tryConnect(inputFragment.getConnectionArea(), getProgrammingBlock(inputFragment.connectedTo).getFirstConnector());
        reconnectBlock(inputFragment.connectedTo.getCoreBlock().data);
    }

    private static void reconnectDescendant(BlockData data) {
        ConnectionRules.tryConnect(getProgrammingBlock(data).getDownFacingConnector(), getProgrammingBlock(data.getDescendant()).getFirstConnector());
        reconnectBlock(data.getDescendant());
    }
}
