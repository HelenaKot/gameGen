package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public final class ConnectionPlacer {

    public static ArrayList<ConnectionArea> getConnectors(CoreBlock coreBlock) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        coreBlock.getWidth();
        float height = coreBlock.getHeight();
        if (connects(coreBlock, Direction.UP))
            output.add(new ConnectionArea(0, height - padding, coreBlock, Direction.UP));
        if (connects(coreBlock, Direction.DOWN))
            output.add(new ConnectionArea(0, 0, coreBlock, Direction.DOWN));
        if (connects(coreBlock, Direction.LEFT))
            output.add(new ConnectionArea(0, height - padding * 2, coreBlock, Direction.LEFT));
        getInputConnectors(output, coreBlock);
        return output;
    }

    private static boolean connects(CoreBlock coreBlock, Direction dir) {
        return coreBlock.data.shape.connects(dir);
    }

    private static void getInputConnectors(ArrayList<ConnectionArea> output, CoreBlock coreBlock) {
        for (BlockInputAppearance input : coreBlock.blockAppearance.inputs)
            if (input.getConnectors() != null)
                output.add(input.getConnectors());
    }
}
