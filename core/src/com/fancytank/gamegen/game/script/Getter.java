package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

public class Getter implements Executable {
    char type;
    BaseActor blockInstance;

    public Getter(ExecutableProducer producer) {
        type = producer.methodBlock.getValue().charAt(0);
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
    }

    @Override
    public Variable performActionForResults() {
        switch (type) {
            case 'c':
                return new Variable("#" + blockInstance.tint.toString().substring(0, 6), ValueType.COLOR);
            case 'x':
                return new Variable(String.valueOf(blockInstance.x), ValueType.INT_NUMBER);
            case 'y':
                return new Variable(String.valueOf(blockInstance.y), ValueType.INT_NUMBER);
            case 't':
                return new Variable(blockInstance.getClassName(), ValueType.CLASS_NAME);
        }
        return null;
    }
}
