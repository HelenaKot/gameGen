package com.fancytank.gamegen.game.actor;

import java.io.Serializable;

public class CustomActorToInit extends ActorToInit implements Serializable {
    private static final long serialVersionUID = 1233613063064495682L;

    CustomActorToInit(String name, String textureName) {
        super(name, textureName);
    }

    @Override
    BaseActor createInstance(int x, int y) {
        return new GenericActor(x, y, name, getTexture());
    }
}
