package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.GenericActor;
import com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

class BlockColorSetter implements Executable {
    private ExecutableProducer producer;
    BaseActor blockInstance;
    Executable colorGetter;

    BlockColorSetter(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited)
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ProducerTag.VALUE0);
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        colorGetter = initFromProducer(producer.getProducer(ProducerTag.VALUE0), blockInstance);
    }

    @Override
    public boolean performAction() {
        if (blockInstance instanceof GenericActor)
            ((GenericActor) blockInstance).tint = Color.valueOf(colorGetter.performActionForResults().getValue());
        return true;
    }
}
