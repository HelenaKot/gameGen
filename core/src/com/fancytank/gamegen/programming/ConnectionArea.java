package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Rectangle;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionArea implements Collidable {
    BlockActor parent;
    Rectangle boundingBox;
    Direction direction;

    ConnectionArea(float x, float y, BlockActor parent, Direction direction) {
        this.parent = parent;
        this.direction = direction;
        boundingBox = new Rectangle(x, y, padding, padding);
    }

    @Override
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    void translate(float x, float y) {
        boundingBox.setPosition(boundingBox.getX() + x, boundingBox.getY() + y);
    }

    @Override
    public boolean overlapping(Collidable collidable) {
        return this.getBoundingBox().overlaps(collidable.getBoundingBox());
    }

    @Override
    public String toString() {
        return "CA-"+ direction + " PA: "+ parent;
    }
}
