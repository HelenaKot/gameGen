package com.fancytank.gamegen.programming;

import com.fancytank.gamegen.programming.blocks.BlockManager;
import com.fancytank.gamegen.programming.blocks.BlockRestoreEvent;
import com.fancytank.gamegen.programming.blocks.ConnectionRules;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.VariableList;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;

import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public class Workspace {
    public static void populateWorkspace(ProgrammingBlockSavedInstance[] data) {
        clearWorkspace();
        if (data != null) {
            for (ProgrammingBlockSavedInstance block : data) {
                if (block.data.shape == BlockShape.VARIABLE_DECLARATION)
                    VariableList.put(block.data.getValue(), block.data.getVariable());
                EventBus.getDefault().post(new BlockRestoreEvent(block.restore()));
            }
            reconnectAll();
        }
    }

    private static ArrayList<BlockData> createBlockList() {
        ArrayList<BlockData> output = new ArrayList<>();
        for (ProgrammingBlock programmingBlock : BlockManager.getBlocList())
            output.add(programmingBlock.coreBlock.data);
        return output;
    }

    public static void clearWorkspace() {
        ListIterator<ProgrammingBlock> iter = BlockManager.getBlocList().listIterator();
        while (iter.hasNext()) {
            ProgrammingBlock pb = iter.next();
            pb.destroy();
        }
    }

    public static String getDebugLog() {
        String log = "BLOCKS ONBOARD: " + createBlockList().size() + "\n";
        for (BlockData blockData : createBlockList())
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static ProgrammingBlockSavedInstance createSaveInstance(ProgrammingBlock programmingBlock) {
        return new ProgrammingBlockSavedInstance(programmingBlock);
    }

    private static void reconnectAll() {
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
