package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.BaseActor;

public interface MapType {
    MapType init(int width, int height, int heightOffset);

    void changeBlock(BaseActor actor);

    BaseActor[][] getMap();
}
