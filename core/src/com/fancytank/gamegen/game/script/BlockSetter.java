package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.Variable;

import java.util.Vector;

class BlockSetter implements Executable {
    private BlockData blockData;
    BaseActor blockInstance;
    Variable blockClassName;
    Variable blockX, blockY;

    public BlockSetter(BlockData blockData) {
        this.blockData = blockData;
    }

    @Override
    public void init(BaseActor blockInstance) {
        this.blockInstance = blockInstance;
        Vector<Variable> vars = Util.collectVars(blockData);
        blockClassName = vars.get(0);
        blockX = vars.get(1);
        blockY = vars.get(2);
    }

    @Override
    public boolean performAction() {
        MapManager.changeBlock(ActorInitializer.getInstanceOf(blockClassName.getValue(), blockInstance.x + blockX.getInt(), blockInstance.y + blockY.getInt()));
        return true;
    }
}
