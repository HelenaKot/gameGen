package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.MapManager;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.ValueType;

import java.util.Vector;

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
                    ActorInitializer.addActionListener(classNameInput.connectedTo.getValue(), convertToExecutable(methodSocketInput.connectedTo));
            }
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null;//&& inputFragment.connectedTo.hasValue();
    }

    private static Executable convertToExecutable(BlockData methodBlock) {
        if (methodBlock.getInputs()[0].expectedValue == ValueType.CLASS_NAME && methodBlock.getInputs()[0].connectedTo != null)
            return convertToBlockSetter(methodBlock);
        return null;
    }

    private static Executable convertToBlockSetter(final BlockData methodBlock) {
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
                MapManager.changeBlock(
                        ActorInitializer.getInstanceOf(blockClassName, blockInstance.x + vars.get(0), blockInstance.y + vars.get(1)));
                return true;
            }
        };
    }

}
