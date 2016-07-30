package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class VariableSetter implements Executable {
    private ExecutableProducer producer;
    Variable variableToSet;
    Executable newValue;

    public VariableSetter(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited) {
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ExecutableProducer.ProducerTag.VALUE0);
        }
    }

    @Override
    public void init(BaseActor blockInstance) {
        variableToSet = producer.methodBlock.getVariable();
        newValue = initFromProducer(producer.getProducer(ExecutableProducer.ProducerTag.VALUE0), blockInstance);
    }

    @Override
    public boolean performAction() {
        VariableList.put(variableToSet.getDirectValue(), newValue.performActionForResults());
        return true;
    }
}
