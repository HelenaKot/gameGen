package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.script.ExecutableProducer.ProducerTag;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;

public class Loop {
    static Executable whileStatement(ExecutableProducer producer) {
        LoopType whileLoop = (Executable condition, Executable execute) -> {
            while (condition.performAction())
                execute.performAction();
        };
        if (producer.getProducer(ProducerTag.CONDITION_PRODUCER) == null)
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ProducerTag.CONDITION_PRODUCER);
        return new DefaultLoop(producer, producer.getProducer(ProducerTag.CONDITION_PRODUCER), whileLoop);
    }

    static Executable forStatement(ExecutableProducer producer) {
        if (producer.methodBlock.hasValue()) return
                getForTimes(producer, Integer.parseInt(producer.methodBlock.getValue()));
        else
            return new IteratorLoop(producer);
    }

    static Executable getForTimes(ExecutableProducer producer, final int numericValue) {
        LoopType forLoop = (Executable condition, Executable execute) -> {
            for (int i = 0; i < numericValue; i++) {
                execute.performAction();
            }
        };
        return new DefaultLoop(producer, null, forLoop);
    }
}
