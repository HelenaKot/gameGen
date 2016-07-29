package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.GenericActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;

import java.util.Vector;

class BlockColorSetter implements Executable {
    private BlockData blockData;
    BaseActor blockInstance;
    Variable color;

    BlockColorSetter(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        Vector<Variable> vars = Util.collectVars(blockData);
        color = vars.get(0);
    }

    @Override
    public boolean performAction() {
        if (blockInstance instanceof GenericActor)
            ((GenericActor) blockInstance).tint = Color.valueOf(color.getValue());
        return true;
    }
}
