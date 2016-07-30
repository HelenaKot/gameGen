package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.GenericActor;

class BlockColorSetter implements Executable {
    private ExecutableProducer producer;
    BaseActor blockInstance;
    Executable colorGetter;

    BlockColorSetter(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited)
            producer.putProducer(Util.createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ExecutableProducer.ProducerTag.VALUE0);
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        colorGetter = producer.getProducer(ExecutableProducer.ProducerTag.VALUE0).getInstance();
        colorGetter.init(blockInstance);
    }

    @Override
    public boolean performAction() {
        if (blockInstance instanceof GenericActor)
            ((GenericActor) blockInstance).tint = Color.valueOf(colorGetter.performActionForResults());
        return true;
    }
}
