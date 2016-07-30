package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;

class DefaultExecutable implements Executable {
    private BlockData blockData;
    Variable variable;

    public DefaultExecutable(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor blockInstance) {
        variable = blockData.getVariable();
    }

    @Override
    public boolean performAction() {
        return Boolean.parseBoolean(variable.getValue());
    }

    @Override
    public Variable performActionForResults() {
        return variable;
    }
}
