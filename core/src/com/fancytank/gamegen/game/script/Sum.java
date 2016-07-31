package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.Variable;

public class Sum implements Executable {
    ExecutableProducer producer;

    public Sum(ExecutableProducer producer) {
        this.producer = producer;
    }

    @Override
    public void init(BaseActor block) {

    }

    @Override
    public Variable performActionForResults() {
        return null;
    }
}
