package com.fancytank.gamegen.programming;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionPlacer {

    static ArrayList<ConnectionArea> getConnectors(BlockActor blockActor) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        blockActor.getWidth();
        for (Direction direction : Direction.values())
            if (blockActor.shape.connects(direction)) {
                float x = blockActor.getX(), width = blockActor.getWidth();
                float y = blockActor.getY(), height = blockActor.getHeight();
                switch (direction) {
                    case UP:
                        output.add(new ConnectionArea(x, y + height - padding, blockActor, Direction.UP));
                        break;
                    case RIGHT:
                        output.add(new ConnectionArea(x + width - padding, y + padding, blockActor, Direction.RIGHT));
                        break;
                    case DOWN:
                        output.add(new ConnectionArea(x, y, blockActor, Direction.DOWN));
                        break;
                    case LEFT:
                        output.add(new ConnectionArea(x, y + padding, blockActor, Direction.LEFT));
                        break;
                }
            }
        return output;
    }
}
