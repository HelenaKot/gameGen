package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.GenericActor;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.MethodType;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.Vector;

public class ExecutableProducer {
    enum ActionListenerType {ON_PRESS, ON_RELEASE, ON_HOLD, TICK, NONE} //todo not used yet

    BlockData methodBlock;
    ActionListenerType type;
    MethodType methodType;

    ExecutableProducer(BlockData methodBlock, ActionListenerType type) {
        this.methodBlock = methodBlock;
        this.type = type;
        methodType = getMethodType();
    }

    private MethodType getMethodType() {
        for (InputFragment inputFragment : methodBlock.getInputs())
            if (inputFragment.expectedValue != null && inputFragment.expectedValue.hasExpectedMethod())
                return inputFragment.getExpectedMethod();
        return null;
    }

    public Executable getInstance() {
        switch (getMethodType()) {
            case BLOCK_SETTR:
                return getBlockSetter();
            case COLOR_SETTER:
                return getBlockColorChanger();
            case LOGIC_STATEMENT:
                return getLogicStatement();
            case CONDITIONED_STATEMENT:
                return getIfStatement();
            default:
                return null;
        }
    }

    private Executable getBlockSetter() {
        return new Executable() {
            BaseActor blockInstance;
            String blockClassName;
            int blockX, blockY;

            @Override
            public void init(BaseActor blockInstance) {
                System.out.println(methodBlock);
                this.blockInstance = blockInstance;
                Vector<String> vars = collectVars();
                blockClassName = vars.get(0);
                blockX = Integer.parseInt(vars.get(1));
                blockY = Integer.parseInt(vars.get(2));
            }

            @Override
            public boolean performAction() {
                MapManager.changeBlock(
                        ActorInitializer.getInstanceOf(blockClassName, blockInstance.x + blockX, blockInstance.y + blockY));
                return true;
            }
        };
    }

    private Executable getBlockColorChanger() {
        return new Executable() {
            BaseActor blockInstance;
            Vector<String> vars;
            Color color;

            @Override
            public void init(BaseActor blockInstance) {
                this.blockInstance = blockInstance;
                vars = collectVars();
                color = Color.valueOf(vars.get(0));
            }

            @Override
            public boolean performAction() {
                if (blockInstance instanceof GenericActor)
                    ((GenericActor) blockInstance).tint = color;
                return true;
            }
        };
    }

    //todo placeholder
    private Executable getLogicStatement() {
        return new Executable() {
            boolean value;

            @Override
            public void init(BaseActor block) {
                value = true;
            }

            @Override
            public boolean performAction() {
                return value;
            }
        };
    }

    private Executable getIfStatement() {
        return new Executable() {
            Executable condition;
            Executable execute;
            boolean validBlock = false;

            @Override
            public void init(BaseActor block) {
                condition = initSubBlock(block, methodBlock.getInputs()[0].connectedTo);
                execute = initSubBlock(block, methodBlock.getInputs()[1].connectedTo);
                if (condition != null && execute != null)
                    validBlock = true;
            }

            @Override
            public boolean performAction() {
                if (validBlock && condition.performAction())
                    execute.performAction();
                return true;
            }
        };
    }

    private Executable initSubBlock(BaseActor block, BlockData blockData) {
        if (blockData != null) {
            Executable executable = new ExecutableProducer(blockData, ActionListenerType.NONE).getInstance();
            executable.init(block);
            return executable;
        }
        return null;
    }

    private Vector<String> collectVars() {
        Vector<String> vars = new Vector<String>();
        InputFragment[] inputs = methodBlock.getInputs();
        for (int i = 0; i < inputs.length; i++)
            if (inputs[i].inputType != InputType.DUMMY)
                if (inputs[i].connectedTo != null)
                    vars.add(inputs[i].connectedTo.getValue());
                else vars.add("0");
        return vars;
    }
}
