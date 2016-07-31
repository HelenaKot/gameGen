package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

public class Getter implements Executable {
    char type;
    BaseActor actor;

    public Getter(ExecutableProducer producer) {
        type = producer.methodBlock.getValue().charAt(0);
    }

    @Override
    public void init(BaseActor block) {
        actor = block;
    }

    @Override
    public Variable performActionForResults() {
        switch (type) {
            case 'c':
                return new Variable(String.format("#%06X", (0xFFFFFF & actor.tint.toIntBits())), ValueType.COLOR);
            case 'x':
                return new Variable(String.valueOf(actor.x), ValueType.INT_NUMBER);
            case 'y':
                return new Variable(String.valueOf(actor.y), ValueType.INT_NUMBER);
        }
        return null;
    }
}
