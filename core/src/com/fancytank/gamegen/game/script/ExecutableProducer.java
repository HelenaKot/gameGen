package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.MethodType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExecutableProducer {
    enum ActionListenerType {ON_PRESS, ON_RELEASE, ON_HOLD, TICK, NONE} //todo not used yet

    enum ProducerTag {CONDITION_PRODUCER, EXECUTION_PRODUCER, SECONDARY_PRODUCER, VALUE0, VALUE1, VALUE2}

    BlockData methodBlock;
    ActionListenerType type;
    MethodType methodType;
    boolean producersInited = false;

    private HashMap<ProducerTag, ExecutableProducer> producers;
    private ArrayList<ExecutableProducer> instructions;

    ExecutableProducer(BlockData methodBlock, ActionListenerType type) {
        initProducer(methodBlock);
        this.type = type;
        if (methodBlock.hasDescendant())
            collectDescendants();
    }

    private ExecutableProducer(BlockData methodBlock) {
        initProducer(methodBlock);
    }

    private void initProducer(BlockData methodBlock) {
        this.methodBlock = methodBlock;
        methodType = methodBlock.getExpectedMethod();
        instructions = new ArrayList<>();
        producers = new HashMap<>();
    }

    private void collectDescendants() {
        ArrayList<BlockData> instructionBlocks = new ArrayList<BlockData>();
        instructionBlocks.add(methodBlock);
        createList(instructionBlocks, methodBlock);
        for (BlockData block : instructionBlocks)
            instructions.add(new ExecutableProducer(block));
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
                return new BlockColorSetter(this);
            case VARIABLE_SETTER:
                return new VariableSetter(this.methodBlock);
            case COMPARE_STATEMENT:
                return new CompareStatement(this.methodBlock);
            case LOOP_WHILE:
                return Loop.whileStatement(this);
            case LOOP_FOR:
                return Loop.forStatement(this);
            case IF_STATEMENT:
                return new IfStatement(this);
            case GETTER:

            case SUM:

            default:
                return new DefaultExecutable(this.methodBlock);
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

    void putProducer(ExecutableProducer producer, ProducerTag tag) {
        producers.put(tag, producer);
        producersInited = true;
    }

    ExecutableProducer getProducer(ProducerTag tag) {
        return producers.get(tag);
    }
}
