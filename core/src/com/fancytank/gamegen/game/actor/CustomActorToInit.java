package com.fancytank.gamegen.game.actor;

public class CustomActorToInit extends ActorToInit {
    CustomActorToInit(String name, String textureName) {
        super(name, textureName);
    }

    @Override
    BaseActor createInstance(int x, int y) {
        return new GenericActor(x, y, tile.name, tile.getTexture());
    }
}
