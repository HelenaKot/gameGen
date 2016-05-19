package com.fancytank.gamegen.programming.looks;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;

public final class Utility {

    public static Vector2 myLocalToStageCoordinates(Actor actor) {
        float x = actor.getX(), y = actor.getY();
        while (actor.hasParent()) {
            actor = actor.getParent();
            x += actor.getX();
            y += actor.getY();
        }
        return new Vector2(x, y);
    }

    public static ProgrammingBlock getProgrammingBlock(ConnectionArea connectionArea) {
        return connectionArea.coreBlock.getProgrammingBlock();
    }

}
