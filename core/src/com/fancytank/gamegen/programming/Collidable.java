package com.fancytank.gamegen.programming;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getBoundingBox();
    boolean overlapping(Collidable collidable);
}
