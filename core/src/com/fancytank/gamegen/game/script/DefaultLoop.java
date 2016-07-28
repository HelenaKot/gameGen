package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;

class DefaultLoop implements Executable {
    private ExecutableProducer executableProducer;
    private final ExecutableProducer conditionProducer;
    private final LoopType loop;
    Executable condition;
    Executable execute;

    public DefaultLoop(ExecutableProducer executableProducer, ExecutableProducer conditionProducer, LoopType loop) {
        this.executableProducer = executableProducer;
        this.conditionProducer = conditionProducer;
        this.loop = loop;
        initProducers();
    }

    private void initProducers() {
        if (executableProducer.getProducer(ExecutableProducer.ProducerTag.EXECUTION_PRODUCER) == null)
            executableProducer.putProducer(createSubBlock(executableProducer.methodBlock.getInputs()[1].connectedTo), ExecutableProducer.ProducerTag.EXECUTION_PRODUCER);
    }

    @Override
    public void init(BaseActor block) {
        execute = executableProducer.getProducer(ExecutableProducer.ProducerTag.EXECUTION_PRODUCER).getInstance();
        execute.init(block);
        if (conditionProducer != null) {
            condition = conditionProducer.getInstance();
            condition.init(block);
        }
    }

    @Override
    public boolean performAction() {
        loop.execute(condition, execute);
        return true;
    }
}
