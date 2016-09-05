package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class IfStatement implements Executable {
    private ExecutableProducer producer;
    Boolean doElse = false;
    Executable condition, execution1, execution2;

    public IfStatement(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited)
            initProducers();
    }

    private void initProducers() {
        producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ProducerTag.CONDITION_PRODUCER);
        producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[1].connectedTo), ProducerTag.EXECUTION_PRODUCER);
        if (producer.methodBlock.getInputs().length > 2)
            producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[3].connectedTo), ProducerTag.SECONDARY_PRODUCER);
    }

    @Override
    public void init(BaseActor blockInstance) {
        condition = initFromProducer(producer.getProducer(ProducerTag.CONDITION_PRODUCER), blockInstance);
        execution1 = initFromProducer(producer.getProducer(ProducerTag.EXECUTION_PRODUCER), blockInstance);
        if (producer.methodBlock.getInputs().length > 2) {
            execution2 = initFromProducer(producer.getProducer(ProducerTag.SECONDARY_PRODUCER), blockInstance);
            doElse = true;
        }
    }

    @Override
    public boolean performAction() {
        if (condition.performAction())
            execution1.performAction();
        else if (doElse)
            execution2.performAction();
        return true;
    }
}
