package com.fancytank.gamegen.programming.looks;

import com.fancytank.gamegen.programming.Direction;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class ConnectionPlacer {

    public static ArrayList<ConnectionArea> getConnectors(CoreBlock coreBlock) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        coreBlock.getWidth();
        for (Direction direction : Direction.values())
            if (coreBlock.data.shape.connects(direction)) {
                float width = coreBlock.getWidth(), height = coreBlock.getHeight();
                switch (direction) {
                    case UP:
                        output.add(new ConnectionArea(0, height - padding, coreBlock, Direction.UP));
                        break;
                    case RIGHT:
                        output.add(new ConnectionArea(0 + width - padding, padding, coreBlock, Direction.RIGHT));
                        break;
                    case DOWN:
                        output.add(new ConnectionArea(0, 0, coreBlock, Direction.DOWN));
                        break;
                    case LEFT:
                        output.add(new ConnectionArea(0, height - padding * 2, coreBlock, Direction.LEFT));
                        break;
                }
            }
        return output;
    }
}
