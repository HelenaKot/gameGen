package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;

class VariableSetter implements Executable {
    private BlockData blockData;
    Variable variableToSet;
    Variable newValue;

    public VariableSetter(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor blockInstance) {
        variableToSet = blockData.getVariable();
        BlockData connectedTo = blockData.getInputs()[0].connectedTo;
        if (connectedTo != null)
            newValue = connectedTo.getVariable();
    }

    @Override
    public boolean performAction() {
        VariableList.put(variableToSet.getDirectValue(), newValue);
        return true;
    }
}
