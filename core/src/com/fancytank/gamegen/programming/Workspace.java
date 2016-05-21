package com.fancytank.gamegen.programming;


import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.looks.BlockShape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Workspace {

    public static void save() {
    }

    public static void load() {
    }

    public static String getDebugLog() {
        String log = "DEBUG LOG\n";
        for (BlockData blockData : BlockData.getBlockDataList())
            if (blockData.shape == BlockShape.ENCLOSED)
                log += blockData.toString() + "\n\n";
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

    static private class ProgrammingBlockSavedInstance implements Serializable {
        BlockData data;
        float a, r, g, b;
        float posX, posY;

        ProgrammingBlockSavedInstance(ProgrammingBlock programmingBlock) {
            data = programmingBlock.coreBlock.data;
            a = programmingBlock.coreBlock.tint.a;
            r = programmingBlock.coreBlock.tint.r;
            g = programmingBlock.coreBlock.tint.g;
            b = programmingBlock.coreBlock.tint.b;
            posX = programmingBlock.getX();
            posY = programmingBlock.getY();
        }

        ProgrammingBlock restore() {
            ProgrammingBlock restoredBlock = new ProgrammingBlock(data, new Color(r, g, b, a));
            data.setCoreBlock(restoredBlock.coreBlock);
            restoredBlock.setPosition(posX, posY);
            return restoredBlock;
        }
    }
}
