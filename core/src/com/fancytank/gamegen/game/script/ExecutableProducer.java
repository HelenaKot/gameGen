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

    private ExecutableProducer conditionProducer, executionProducer;

    ExecutableProducer(BlockData methodBlock, ActionListenerType type) {
        this.methodBlock = methodBlock;
        this.type = type;
        methodType = getMethodType();
    }

    private MethodType getMethodType() {
        for (InputFragment inputFragment : methodBlock.getInputs())
            return inputFragment.getExpectedMethod();
        return null;
    }

    public Executable getInstance() {
        switch (methodType) {
            case BLOCK_SETTER:
                return getBlockSetter();
            case COLOR_SETTER:
                return getBlockColorChanger();
            case LOGIC_STATEMENT:
                return getLogicStatement();
            case LOOP_WHILE:
                return getWhileStatement();
            case LOOP_FOR:
                return getForStatement();
            case IF_STATEMENT:
                return getIfStatement();
        }
        return null;
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
                if (executionProducer == null) {
                    condition = createSubBlock(conditionProducer, methodBlock.getInputs()[0].connectedTo).getInstance();
                    execute = createSubBlock(executionProducer, methodBlock.getInputs()[1].connectedTo).getInstance();
                }
                if (condition != null && execute != null) {
                    condition.init(block);
                    execute.init(block);
                    validBlock = true;
                }
            }

            @Override
            public boolean performAction() {
                if (validBlock && condition.performAction())
                    execute.performAction();
                return true;
            }
        };
    }

    private Executable getWhileStatement() {
        LoopType whileLoop = new LoopType() {
            public void execute(Executable condition, Executable execute) {
                while (condition.performAction())
                    execute.performAction();
            }
        };
        return getGenericLoop(null, whileLoop);
    }

    private Executable getForStatement() {
        String value;
        if (methodBlock.hasValue()) {
            value = methodBlock.getValue();
            getForTimes(Integer.parseInt(value));
            //todo dla listy
        }
        return null;
    }

    private Executable getForTimes(final int numericValue) {
        LoopType forLoop = new LoopType() {
            public void execute(Executable condition, Executable execute) {
                for (int i = 0; i < numericValue; i++)
                    execute.performAction();
            }
        };
        return getGenericLoop(null, forLoop);
    }

    private Executable getGenericLoop(Executable condition, final LoopType loop) {
        return new Executable() {
            Executable condition;
            Executable execute;

            @Override
            public void init(BaseActor block) {
                if (execute == null) {
                    execute = createSubBlock(executionProducer, methodBlock.getInputs()[1].connectedTo).getInstance();
                    execute.init(block);
                }
                if (condition != null)
                    condition.init(block);
            }

            @Override
            public boolean performAction() {
                loop.execute(condition, execute);
                return true;
            }
        };
    }

    private interface LoopType {
        void execute(final Executable condition0, final Executable execute0);
    }

    private ExecutableProducer createSubBlock(ExecutableProducer variable, BlockData blockData) {
        if (blockData != null) {
            variable = new ExecutableProducer(blockData, ActionListenerType.NONE);
            return variable;
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
