package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

public class Sum implements Executable {
    ExecutableProducer producer;
    Executable var0, var1;

    public Sum(ExecutableProducer producer) {
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
    }

    @Override
    public Variable performActionForResults() {
        return getSum(var0.performActionForResults(), var1.performActionForResults());
    }

    private Variable getSum(Variable var0, Variable var1) {
        float value = Float.parseFloat(var0.getValue()) + Float.parseFloat(var1.getValue());
        return (var0.valueType == ValueType.INT_NUMBER) ?
                new Variable(Integer.toString((int) value), ValueType.INT_NUMBER) :
                new Variable(Float.toString(value), ValueType.NUMBER);
    }
}
