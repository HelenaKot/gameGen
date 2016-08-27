package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.VariableList;

import static com.fancytank.gamegen.game.script.ExecutableProducer.ActionListenerType;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        for (ProgrammingBlockSavedInstance savedBlock : data)
            switch (savedBlock.data.shape) {
                case ACTION_LISTENER:
                    loadListener(savedBlock.data);
                    break;
                case VARIABLE_DECLARATION:
                    loadVariable(savedBlock.data);
                    break;
                case TIMER:
                    loadTimer(savedBlock.data);
                    break;
            }
    }

    private static void loadVariable(BlockData savedBlock) {
        InputFragment valueInput = savedBlock.getInputs()[0];
        if (hasValidConnection(valueInput)) {
            String name = savedBlock.getValue();
            ValueType type = savedBlock.getVariable().valueType;
            String value = valueInput.connectedTo.getValue();
            VariableList.put(name, value, type);
        }
    }

    private static void loadListener(BlockData blockData) {
        InputFragment classNameInput = blockData.getInputs()[0];
        InputFragment methodSocketInput = blockData.getInputs()[2];
        if (hasValidConnection(classNameInput) && methodSocketInput.connectedTo != null)
            createActionListener(classNameInput.connectedTo.getValue(), methodSocketInput.connectedTo);
    }

    private static void loadTimer(BlockData blockData) {
        InputFragment delayInput = blockData.getInputs()[0];
        InputFragment classNameInput = blockData.getInputs()[1];
        InputFragment methodSocketInput = blockData.getInputs()[2];
        if (hasValidConnection(delayInput) && hasValidConnection(classNameInput) && methodSocketInput.connectedTo != null)
            createTimerAction(delayInput.connectedTo.getValue(), classNameInput.connectedTo.getValue(), methodSocketInput.connectedTo);
    }

    private static void createTimerAction(String delayInput, String className, BlockData executableBlock) {
        ExecutableProducer executableProducer = new ExecutableProducer(executableBlock, Integer.parseInt(delayInput));
        if (executableProducer.getInstance() != null)
            ActorInitializer.addTimerListener(className, executableProducer);
        else
            System.out.println("Class " + className + " have unparsable per tick executable.");
    }

    private static void createActionListener(String className, BlockData executableBlock) {
        ExecutableProducer executableProducer = new ExecutableProducer(executableBlock, ActionListenerType.ON_PRESS);
        if (executableProducer.getInstance() != null)
            ActorInitializer.addActionListener(className, executableProducer);
        else
            System.out.println("Class " + className + " have unparsable executable.");
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null;
    }

}
