package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Rectangle;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionArea implements Collidable {
    BlockActor parent;
    Rectangle boundingBox;

    ConnectionArea(int x, int y, BlockActor parent) {
        this.parent = parent;
        boundingBox = new Rectangle(x, y, padding, padding);
    }

    @Override
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

}
