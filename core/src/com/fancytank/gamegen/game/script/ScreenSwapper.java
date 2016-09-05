package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.game.map.MapManager;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;
import static com.fancytank.gamegen.game.script.Util.initFromProducer;

public class ScreenSwapper implements Executable {
    private ExecutableProducer producer;
    Executable screenName;

    ScreenSwapper(ExecutableProducer producer) {
        this.producer = producer;
        if (!producer.producersInited)
            producer.putProducer(createSubBlock(producer.methodBlock.getInputs()[0].connectedTo), ExecutableProducer.ProducerTag.VALUE0);
    }

    @Override
    public void init(BaseActor blockInstance) {
        screenName = initFromProducer(producer.getProducer(ExecutableProducer.ProducerTag.VALUE0), blockInstance);
    }

    @Override
    public boolean performAction() {
        String boardName = screenName.performActionForResults().getValue();
        MapManager.setBoard(BoardManager.get(boardName));
        return true;
    }
}
