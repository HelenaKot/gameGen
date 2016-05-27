package com.fancytank.gamegen.generated.actor;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.generated.BaseActor;

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
