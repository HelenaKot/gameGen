package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;

class IfStatement implements Executable {
    private BlockData blockData;
    Boolean doElse = false;
    Executable condition, execution1, execution2;

    public IfStatement(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor blockInstance) {
        condition = Util.createSubBlock(blockData.getInputs()[0].connectedTo).getInstance();
        condition.init(blockInstance);
        execution1 = Util.createSubBlock(blockData.getInputs()[1].connectedTo).getInstance();
        execution1.init(blockInstance);
        if (blockData.getInputs().length > 2) {
            execution2 = Util.createSubBlock(blockData.getInputs()[3].connectedTo).getInstance();
            execution2.init(blockInstance);
            doElse = true;
        }
    }

    @Override
    public boolean performAction() {
        if (condition.performAction())
            execution1.performAction();
        else if (doElse)
            execution2.performAction();
        return true;
    }
}
