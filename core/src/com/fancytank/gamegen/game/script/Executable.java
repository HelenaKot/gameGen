package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

public interface Executable {
    void init(BaseActor block);

    default boolean performAction() {
        return false;
    }

    default Variable performActionForResults() {
        return new Variable(String.valueOf(performAction()), ValueType.BOOLEAN);
    }
}
