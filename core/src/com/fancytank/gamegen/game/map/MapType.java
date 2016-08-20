package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.BaseActor;

public interface MapType {
    MapType init();

    void setBoard(Board mapBoard);

    void changeBlock(BaseActor actor);

    BaseActor[][] getMap();
}
