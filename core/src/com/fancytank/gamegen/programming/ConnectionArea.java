package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Rectangle;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionArea {
    BlockActor parent;
    Rectangle boundingBox;
    Direction direction;

    ConnectionArea(float x, float y, BlockActor parent, Direction direction) {
        this.parent = parent;
        this.direction = direction;
        boundingBox = new Rectangle(x, y, padding, padding);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    void translate(float x, float y) {
        boundingBox.setPosition(boundingBox.getX() + x, boundingBox.getY() + y);
    }
}
