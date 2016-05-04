package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionPlacer {

    static ArrayList<ConnectionArea> getConnectors(BlockActor blockActor) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        blockActor.getWidth();
        for (Direction direction : Direction.values())
            if (blockActor.shape.connects(direction)) {
                Vector2 pos = blockActor.localToStageCoordinates(new Vector2(0, 0));
                float width = blockActor.getWidth(), height = blockActor.getHeight();
                switch (direction) {
                    case UP:
                        output.add(new ConnectionArea(pos.x, pos.y + height - padding, blockActor, Direction.UP));
                        break;
                    case RIGHT:
                        output.add(new ConnectionArea(pos.x + width - padding, pos.y + padding, blockActor, Direction.RIGHT));
                        break;
                    case DOWN:
                        output.add(new ConnectionArea(pos.x, pos.y, blockActor, Direction.DOWN));
                        break;
                    case LEFT:
                        output.add(new ConnectionArea(pos.x, pos.y + padding, blockActor, Direction.LEFT));
                        break;
                }
            }
        return output;
    }
}
