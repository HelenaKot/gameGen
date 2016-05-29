package com.fancytank.gamegen.programming;


import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.BlockShape;

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

    public static void load(ProgrammingBlockSavedInstance[] data) {
        clearWorkspace();
        for (ProgrammingBlockSavedInstance block : data)
            MainGdx.addToStage(block.restore());
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

}
