package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class CompareStatement implements Executable {
    private ExecutableProducer producer;
    Executable var0, var1;
    Compare statement;

    public CompareStatement(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited) {
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ExecutableProducer.ProducerTag.VALUE0);
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[1].connectedTo), ExecutableProducer.ProducerTag.VALUE1);
        }
    }

    @Override
    public void init(BaseActor blockInstance) {
        var0 = initFromProducer(producer.getProducer(ExecutableProducer.ProducerTag.VALUE0), blockInstance);
        var1 = initFromProducer(producer.getProducer(ExecutableProducer.ProducerTag.VALUE1), blockInstance);
        switch (producer.methodBlock.getValue().charAt(0)) {
            case '<':
                statement = (var0, var1) -> 0 > compareTo(var0, var1);
                break;
            case '=':
                statement = (var0, var1) -> 0 == compareTo(var0, var1);
                break;
            case '>':
                statement = (var0, var1) -> 0 < compareTo(var0, var1);
                break;
            default:
                statement = (var0, var1) -> false;
                break;
        }
    }

    @Override
    public boolean performAction() {
        return statement.compare(var0.performActionForResults(), var1.performActionForResults());
    }

    private interface Compare {
        boolean compare(Variable var0, Variable var1);
    }

    private int compareTo(Variable var0, Variable var1) {
        if ((var0.valueType == ValueType.NUMBER || var0.valueType == ValueType.INT_NUMBER)
                && (var1.valueType == ValueType.NUMBER || var1.valueType == ValueType.INT_NUMBER))
            return Float.compare(Float.parseFloat(var0.getValue()), Float.parseFloat(var1.getValue()));
        else
            return var0.getValue().compareTo(var1.getValue());
    }
}
