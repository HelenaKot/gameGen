package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionPlacer {

    static ArrayList<ConnectionArea> getConnectors(CoreBlock coreBlock) {
        ArrayList<ConnectionArea> output = new ArrayList<ConnectionArea>();
        coreBlock.getWidth();
        for (Direction direction : Direction.values())
            if (coreBlock.shape.connects(direction)) {
                Vector2 pos = coreBlock.localToStageCoordinates(new Vector2(0, 0));
                float width = coreBlock.getWidth(), height = coreBlock.getHeight();
                switch (direction) {
                    case UP:
                        output.add(new ConnectionArea(pos.x, pos.y + height - padding, coreBlock, Direction.UP));
                        break;
                    case RIGHT:
                        output.add(new ConnectionArea(pos.x + width - padding, pos.y + padding, coreBlock, Direction.RIGHT));
                        break;
                    case DOWN:
                        output.add(new ConnectionArea(pos.x, pos.y, coreBlock, Direction.DOWN));
                        break;
                    case LEFT:
                        output.add(new ConnectionArea(pos.x, pos.y + padding, coreBlock, Direction.LEFT));
                        break;
                }
            }
        return output;
    }
}
