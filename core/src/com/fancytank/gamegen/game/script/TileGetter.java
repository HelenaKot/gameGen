package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;
import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag.EXECUTION_PRODUCER;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class TileGetter implements Executable {
    private ExecutableProducer producer;
    Executable tileX, tileY, execution;

    public TileGetter(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited)
            initProducers();
    }

    private void initProducers() {
        producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[1].connectedTo), ProducerTag.VALUE1);
        producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[2].connectedTo), ProducerTag.VALUE2);
        producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[3].connectedTo), ProducerTag.EXECUTION_PRODUCER);
    }

    @Override
    public void init(BaseActor blockInstance) {
        tileX = initFromProducer(producer.getProducer(ProducerTag.VALUE1), blockInstance);
        tileY = initFromProducer(producer.getProducer(ProducerTag.VALUE2), blockInstance);
        execution = producer.getProducer(EXECUTION_PRODUCER).getInstance(); // needs to be inited with new block
    }

    @Override
    public boolean performAction() {
        BaseActor block = MapManager.getBlock(getPos(tileX), getPos(tileY));
        execution.init(block);
        return execution.performAction();
    }

    private int getPos(Executable executable) {
        return (int) Float.parseFloat(executable.performActionForResults().getValue());
    }
}
