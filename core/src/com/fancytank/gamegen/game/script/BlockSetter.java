package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;
import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class BlockSetter implements Executable {
    private ExecutableProducer producer;
    BaseActor blockInstance;
    Executable blockClassName, blockX, blockY;

    public BlockSetter(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited) {
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ProducerTag.VALUE0);
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[2].connectedTo), ProducerTag.VALUE1);
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[3].connectedTo), ProducerTag.VALUE2);
        }
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        blockClassName = initFromProducer(producer.getProducer(ProducerTag.VALUE0), blockInstance);
        blockX = initFromProducer(producer.getProducer(ProducerTag.VALUE1), blockInstance);
        blockY = initFromProducer(producer.getProducer(ProducerTag.VALUE2), blockInstance);
    }

    @Override
    public boolean performAction() {
        MapManager.changeBlock(ActorInitializer.getInstanceOf(blockClassName.performActionForResults().getValue(),
                blockInstance.x + Integer.parseInt(blockX.performActionForResults().getValue()),
                blockInstance.y + Integer.parseInt(blockY.performActionForResults().getValue())));
        return true;
    }
}
