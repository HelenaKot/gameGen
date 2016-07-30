package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;

public interface Executable {
    void init(BaseActor block);

    boolean performAction();

    default String performActionForResults() {
        return String.valueOf(performAction());
    }
}
