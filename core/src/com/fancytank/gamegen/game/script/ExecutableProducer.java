package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.MethodType;
import com.fancytank.gamegen.programming.data.Variable;

import java.util.ArrayList;
import java.util.List;

import static com.fancytank.gamegen.game.script.Util.createSubBlock;

public class ExecutableProducer {
    enum ActionListenerType {ON_PRESS, ON_RELEASE, ON_HOLD, TICK, NONE} //todo not used yet

    BlockData methodBlock;
    ActionListenerType type;
    MethodType methodType;

    ExecutableProducer conditionProducer, executionProducer;
    private ArrayList<ExecutableProducer> instructions;

    ExecutableProducer(BlockData methodBlock, ActionListenerType type) {
        initProducer(methodBlock);
        this.type = type;
        if (methodBlock.hasDescendant()) {
            ArrayList<BlockData> instructionBlocks = new ArrayList<BlockData>();
            instructionBlocks.add(methodBlock);
            createList(instructionBlocks, methodBlock);
            for (BlockData block : instructionBlocks)
                instructions.add(new ExecutableProducer(block));
        }
    }

    private ExecutableProducer(BlockData methodBlock) {
        initProducer(methodBlock);
    }

    private void initProducer(BlockData methodBlock) {
        this.methodBlock = methodBlock;
        methodType = methodBlock.getExpectedMethod();
        instructions = new ArrayList<ExecutableProducer>();
    }

    private void createList(List<BlockData> list, BlockData blockParent) {
        list.add(blockParent);
        if (blockParent.hasDescendant())
            createList(list, blockParent.getDescendant());
    }

    public Executable getInstance() {
        return (instructions.size() > 1) ? getAggregatedExecutables() : getLocalExecutable();
    }

    private Executable getLocalExecutable() {
        switch (methodType) {
            case BLOCK_SETTER:
                return new BlockSetter(this.methodBlock);
            case COLOR_SETTER:
                return new BlockColorSetter(this.methodBlock);
            case VARIABLE_SETTER:
                return new VariableSetter(this.methodBlock);
            case COMPARE_STATEMENT:
                return new CompareStatement(this.methodBlock);
            case LOOP_WHILE:
                return getWhileStatement();
            case LOOP_FOR:
                return getForStatement();
            case IF_STATEMENT:
                return new IfStatement(this.methodBlock);
            default:
                return getDefault();
        }
    }

    private Executable getAggregatedExecutables() {
        return new Executable() {
            Executable[] executableInstance;

            @Override
            public void init(BaseActor block) {
                executableInstance = new Executable[instructions.size()];
                for (int i = 0; i < instructions.size(); i++) {
                    executableInstance[i] = instructions.get(i).getInstance();
                    executableInstance[i].init(block);
                }
            }

            @Override
            public boolean performAction() {
                for (Executable executable : executableInstance)
                    executable.performAction();
                return true;
            }
        };
    }

    private Executable getDefault() {
        return new Executable() {
            Variable variable;

            @Override
            public void init(BaseActor blockInstance) {
                variable = methodBlock.getVariable();
            }

            @Override
            public boolean performAction() {
                return Boolean.parseBoolean(variable.getValue());
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
        setConditionProducer(methodBlock.getInputs()[0].connectedTo);
        return new DefaultLoop(conditionProducer, whileLoop);
    }

    private Executable getForStatement() {
        String value;
        if (methodBlock.hasValue()) {
            value = methodBlock.getValue();
            return getForTimes(Integer.parseInt(value));
            //todo dla listy
        }
        return null;
    }

    private Executable getForTimes(final int numericValue) {
        LoopType forLoop = new LoopType() {
            public void execute(Executable condition, Executable execute) {
                for (int i = 0; i < numericValue; i++) {
                    execute.performAction();
                }
            }
        };
        return new DefaultLoop(null, forLoop);
    }

    private void setConditionProducer(BlockData methodBlock) {
        if (conditionProducer == null)
            conditionProducer = createSubBlock(methodBlock);
    }

    private interface LoopType {
        void execute(final Executable condition0, final Executable execute0);
    }

    private class DefaultLoop implements Executable {
        private final ExecutableProducer conditionProducer;
        private final LoopType loop;
        Executable condition;
        Executable execute;

        public DefaultLoop(ExecutableProducer conditionProducer, LoopType loop) {
            this.conditionProducer = conditionProducer;
            this.loop = loop;
        }

        @Override
        public void init(BaseActor block) {
            if (executionProducer == null)
                executionProducer = createSubBlock(methodBlock.getInputs()[1].connectedTo);
            execute = executionProducer.getInstance();
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
}
