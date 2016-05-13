package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public final class ConnectionPlacer {

    public static ArrayList<ConnectionArea> getConnectors(CoreBlock coreBlock) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        float height = coreBlock.getHeight();
        if (connects(coreBlock, Direction.LEFT))
            output.add(new ConnectionArea(0, height - padding * 2, coreBlock, Direction.LEFT));
        if (connects(coreBlock, Direction.UP))
            output.add(new ConnectionArea(0, height - padding, coreBlock, Direction.UP));
        if (connects(coreBlock, Direction.DOWN))
            output.add(new ConnectionArea(0, 0, coreBlock, Direction.DOWN));
        getInputConnectors(output, coreBlock);
        return output;
    }

    public static void updateConnectors(CoreBlock coreBlock) {
        ArrayList<ConnectionArea> connectors = coreBlock.parent.connectors;
        float height = coreBlock.getHeight();
        int next = 0;
        if (connects(coreBlock, Direction.LEFT))
            connectors.get(next++).setPosition(0, height - padding * 2);
        if (connects(coreBlock, Direction.UP))
            connectors.get(next++).setPosition(0, height - padding);
        if (connects(coreBlock, Direction.DOWN))
            connectors.get(next++).setPosition(0, 0);
        updateInputConnectors(coreBlock, next);
    }

    private static boolean connects(CoreBlock coreBlock, Direction dir) {
        return coreBlock.data.shape.connects(dir);
    }

    private static void getInputConnectors(ArrayList<ConnectionArea> output, CoreBlock coreBlock) {
        for (BlockInputAppearance input : coreBlock.blockAppearance.inputs)
            if (input.getConnectors() != null)
                output.add(input.getConnectors());
    }

    private static void updateInputConnectors(CoreBlock coreBlock, int index) {
        ArrayList<ConnectionArea> connectors = coreBlock.parent.connectors;
        for (int next = index; next < connectors.size(); next++) {
            Vector2 newPlacement = coreBlock.blockAppearance.inputs.get(next - index).getConnectorPlacement();
            connectors.get(next).setPosition(newPlacement.x/2, newPlacement.y/2);
        }
    }
}
