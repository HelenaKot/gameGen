package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.ValueType;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        System.out.println("load debug");
        for (ProgrammingBlockSavedInstance a : data)
            System.out.println(a);
        loadListeners(data);
    }

    private static void loadListeners(ProgrammingBlockSavedInstance[] data) {
        for (ProgrammingBlockSavedInstance savedBlock : data)
            if (savedBlock.data.shape == BlockShape.ACTION_LISTENER) {
                //todo pierwszy input - najpewniej bedzie wyznacznikiem tego, co robi metoda
                InputFragment classNameInput = savedBlock.data.getInputs()[0];
                InputFragment methodSocketInput = savedBlock.data.getInputs()[2]; // todo or not todo?
                if (hasValidConnection(classNameInput) && hasValidConnection(methodSocketInput))
                    ActorInitializer.addActionListener(classNameInput.connectedTo.getValue(), convertToExecutableProducer(methodSocketInput.connectedTo));
            }
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null;//&& inputFragment.connectedTo.hasValue();
    }

    private static ExecutableProducer convertToExecutableProducer(BlockData methodBlock) {
        if (methodBlock.getInputs()[0].expectedValue == ValueType.CLASS_NAME && methodBlock.getInputs()[0].connectedTo != null)
            return new ExecutableProducer(methodBlock, ExecutableProducer.ExecutableType.ON_PRESS);
        return null;
    }

}
