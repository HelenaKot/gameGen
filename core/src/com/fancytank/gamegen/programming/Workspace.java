package com.fancytank.gamegen.programming;


import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.programming.blocks.ConnectionRules;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.VariableList;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ListIterator;

public class Workspace {

    public static ProgrammingBlockSavedInstance[] getWorkspaceItemsToSave() {
        ProgrammingBlockSavedInstance[] output = new ProgrammingBlockSavedInstance[ProgrammingBlock.getBlockList().size()];
        int index = 0;
        for (ProgrammingBlock pb : ProgrammingBlock.getBlockList())
            output[index++] = new ProgrammingBlockSavedInstance(pb);
        return output;
    }

    public static void loadBlocks(ProgrammingBlockSavedInstance[] data) {
        clearWorkspace();
        if (data != null) {
            for (ProgrammingBlockSavedInstance block : data) {
                if (block.data.shape == BlockShape.VARIABLE_DECLARATION)
                    VariableList.put(block.data.getValue(), block.data.getVariable());
                MainGdx.addToStage(block.restore());
            }
            reconnectAll();
        }
    }

    public static void clearWorkspace() {
        ListIterator iter = ProgrammingBlock.getBlockList().listIterator();
        while (iter.hasNext()) {
            ProgrammingBlock pb = (ProgrammingBlock) iter.next();
            pb.destroy();
        }
    }

    public static String getDebugLog() {
        String log = "BLOCKS ONBOARD: " + BlockData.getBlockDataList().size() + "\n";
        for (BlockData blockData : BlockData.getBlockDataList())
            if (blockData.shape == BlockShape.ACTION_LISTENER)
                log += "BLOCK\n" + blockData.getDebugLog("") + "\n\n";
        return log;
    }

    public static ProgrammingBlock clone(ProgrammingBlock cloningBlock) {
        try {
            ProgrammingBlockSavedInstance pbsi = createSaveInstance(cloningBlock);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(pbsi);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            ProgrammingBlockSavedInstance clone = (ProgrammingBlockSavedInstance) new ObjectInputStream(bais).readObject();
            return clone.restore();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ProgrammingBlockSavedInstance createSaveInstance(ProgrammingBlock programmingBlock) {
        return new ProgrammingBlockSavedInstance(programmingBlock);
    }

    private static void reconnectAll() {
        for (ProgrammingBlock programmingBlock : ProgrammingBlock.getBlockList())
            if (programmingBlock.coreBlock.data.shape.enclosed())
                reconnectBlock(programmingBlock.coreBlock.data);
    }

    private static void reconnectBlock(BlockData data) {
        for (InputFragment inputFragment : data.getInputs())
            if (inputFragment.connectedTo != null)
                reconnectInput(inputFragment);
        if (data.hasDescendant())
            reconnectBlock(data.getDescendant());
    }

    private static void reconnectInput(InputFragment inputFragment) {
        ConnectionRules.tryConnect(inputFragment.getConnectionArea(), inputFragment.connectedTo.getCoreBlock().getProgrammingBlock().getFirstConnector());
        reconnectBlock(inputFragment.connectedTo.getCoreBlock().data);
    }

}
