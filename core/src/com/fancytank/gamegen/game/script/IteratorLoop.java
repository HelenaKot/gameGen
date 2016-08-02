package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;

import java.util.List;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag.EXECUTION_PRODUCER;
import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag.VALUE0;
import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

public class IteratorLoop implements Executable {
    ExecutableProducer producer;
    Executable className, execute;

    public IteratorLoop(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited) {
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), VALUE0);
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[1].connectedTo), EXECUTION_PRODUCER);
        }
    }

    @Override
    public void init(BaseActor blockInstance) {
        className = initFromProducer(producer.getProducer(VALUE0), blockInstance);
        execute = producer.getProducer(EXECUTION_PRODUCER).getInstance();
    }

    @Override
    public boolean performAction() {
        String name = className.performActionForResults().getValue();
        List<BaseActor> actorsList = MapManager.getBlocksOfClass(name);
        for (BaseActor actor : actorsList) {
            execute.init(actor);
            execute.performAction();
        }
        return true;
    }
}