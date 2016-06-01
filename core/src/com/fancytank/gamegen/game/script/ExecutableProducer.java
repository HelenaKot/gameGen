package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ValueType;

import java.util.Vector;

public class ExecutableProducer {
    enum ExecutableType {ON_PRESS, ON_RELEASE, ON_HOLD, TICK} //todo not used yet

    BlockData methodBlock;
    ExecutableType type;

    ExecutableProducer(BlockData methodBlock, ExecutableType type) {
        this.methodBlock = methodBlock;
        this.type = type;
    }

    public Executable getInstance() {
        return new Executable() {
            String blockClassName;
            BaseActor blockInstance;
            Vector<Integer> vars = new Vector<Integer>();

            @Override
            public void init(BaseActor blockInstance) {
                InputFragment[] inputs = methodBlock.getInputs();
                this.blockInstance = blockInstance;
                blockClassName = inputs[0].connectedTo.getValue();
                for (int i = 1; i < inputs.length; i++)
                    if (inputs[i].expectedValue == ValueType.INT_NUMBER)
                        if (inputs[i].connectedTo != null)
                            vars.add(Integer.parseInt(inputs[i].connectedTo.getValue()));
                if (vars.size() < 2)
                    vars.addAll(new Vector<Integer>(0, 0));
            }

            @Override
            public boolean performAction() {
                System.out.println("mapchange " + blockInstance.x + " " + blockInstance.y);
                MapManager.changeBlock(
                        ActorInitializer.getInstanceOf(blockClassName, blockInstance.x + vars.get(0), blockInstance.y + vars.get(1)));
                return true;
            }
        };
    }

}
