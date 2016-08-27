package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

public class Timer implements Executable{
    private ExecutableProducer producer;
    Executable executeOnTime;
    BaseActor blockInstance;
    int delay, myDelay = 0;

    public Timer(ExecutableProducer producer, int delay) {
        this.producer = producer;
        this.delay = delay;
        producer.putProducer(createSubBlock(producer.methodBlock), ExecutableProducer.ProducerTag.EXECUTION_PRODUCER);
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        executeOnTime = initFromProducer(producer.getProducer(ExecutableProducer.ProducerTag.EXECUTION_PRODUCER), blockInstance);
    }

    @Override
    public boolean performAction() {
        myDelay++;
        if (myDelay == delay) {
            myDelay = 0;
            return executeOnTime.performAction();
        } else return false;
    }
}
