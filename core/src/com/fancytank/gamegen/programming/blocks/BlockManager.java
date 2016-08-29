package com.fancytank.gamegen.programming.blocks;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockManager {
    private static CopyOnWriteArrayList<ProgrammingBlock> blockList = new CopyOnWriteArrayList<>();

    public static void add(ProgrammingBlock block) {
        blockList.add(block);
    }

    public static CopyOnWriteArrayList<ProgrammingBlock> getBlocList() {
        return blockList;
    }

    public static ProgrammingBlockSavedInstance[] getWorkspaceItemsToSave() {
        LinkedList<ProgrammingBlockSavedInstance> output = new LinkedList<>();
        for (ProgrammingBlock pb : getBlocList())
            output.add(new ProgrammingBlockSavedInstance(pb));
        return output.toArray(new ProgrammingBlockSavedInstance[output.size()]);
    }

    public static void dispose() {
        blockList = new CopyOnWriteArrayList<>();
    }
}
