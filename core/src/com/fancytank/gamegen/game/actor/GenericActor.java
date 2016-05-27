package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.BaseActor;

public class GenericActor extends BaseActor {

    public GenericActor(Color tint) {
        super(tint);
    }

    public GenericActor(Color tint, int x, int y) {
        super(tint);
        this.x = x;
        this.y = y;
    }
}
