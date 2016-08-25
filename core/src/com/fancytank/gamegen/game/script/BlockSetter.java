package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;
import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class BlockSetter implements Executable {
    private boolean small = false;
    private ExecutableProducer producer;
    BaseActor blockInstance;
    Executable blockClassName, blockX, blockY;

    public BlockSetter(ExecutableProducer producer) {
        this.producer = producer;
        if (producer.methodBlock.getInputs().length == 1)
            small = true;
        if (!producer.producersInited) {
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ProducerTag.VALUE0);
            if (!small) {
                producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[2].connectedTo), ProducerTag.VALUE1);
                producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[3].connectedTo), ProducerTag.VALUE2);
            }
        }
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        blockClassName = initFromProducer(producer.getProducer(ProducerTag.VALUE0), blockInstance);
        if (!small) {
            blockX = initFromProducer(producer.getProducer(ProducerTag.VALUE1), blockInstance);
            blockY = initFromProducer(producer.getProducer(ProducerTag.VALUE2), blockInstance);
        }
    }

    @Override
    public boolean performAction() {
        BaseActor block;
        if (small)
            block = ActorInitializer.getInstanceOf(blockClassName.performActionForResults().getValue(), blockInstance.x, blockInstance.y);
        else
            block = ActorInitializer.getInstanceOf(blockClassName.performActionForResults().getValue(), getPos(blockX), getPos(blockY));
        MapManager.changeBlock(block);
        return true;
    }

    private int getPos(Executable executable) {
        return (int) Float.parseFloat(executable.performActionForResults().getValue());
    }
}
