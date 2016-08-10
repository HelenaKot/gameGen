package com.fancytank.gamegen.game.actor;

public class EmptyActor extends BaseActor {

    public EmptyActor(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public String toString() {
        return "Empty Actor at " + x + " " + y;
    }
}
