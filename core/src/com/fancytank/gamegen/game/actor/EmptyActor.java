package com.fancytank.gamegen.game.actor;

public class EmptyActor extends BaseActor {

    public EmptyActor(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Empty Actor at " + x + " " + y;
    }
}
