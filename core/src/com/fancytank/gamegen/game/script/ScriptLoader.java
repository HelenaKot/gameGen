package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.actor.ActorInitializer;
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
                    ActorInitializer.addActionListener(classNameInput.connectedTo.getValue(), createInputListener(methodSocketInput.connectedTo));
            }
    }

    private static boolean hasValidConnection(InputFragment inputFragment) {
        return inputFragment != null && inputFragment.connectedTo != null ;//&& inputFragment.connectedTo.hasValue();
    }

    private static InputListener createInputListener(final BlockData methodBlock) {
        return new InputListener() {
            Executable myExecutable = convertToExecutable(methodBlock);
            private boolean doInit = true;

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("PYK");
                if (doInit) {
                    myExecutable.init();
                    doInit = false; //todo potem to hurtowo w innym miejscu initnij jak będziesz reflektować adapterty
                }
                myExecutable.performAction();
                return true;
            }
        };
    }

    private static Executable convertToExecutable(BlockData methodBlock) {
        if (methodBlock.getInputs()[0].expectedValue == ValueType.CLASS_NAME && methodBlock.getInputs()[0].connectedTo != null)
            return convertToBlockSetter(methodBlock);
        return null;
    }

    //todo clone or sth, test first
    private static Executable convertToBlockSetter(final BlockData methodBlock) {
        return new Executable() {
            String blockClassName;
            Vector<Integer> vars = new Vector<Integer>();
            @Override
            public void init() {
                InputFragment[] inputs = methodBlock.getInputs();
                blockClassName = inputs[0].connectedTo.getValue();
                for (int i = 1; i < inputs.length; i++)
                    if (inputs[i].expectedValue == ValueType.INT_NUMBER)
                        if (inputs[i].connectedTo != null)
                            vars.add(Integer.parseInt(inputs[i].connectedTo.getValue()));
                if (vars.size() < 2)
                    vars.addAll(new Vector<Integer>(0,0));
            }
            @Override
            public boolean performAction() {
                MapManager.changeBlock(
                        ActorInitializer.getInstanceOf(blockClassName, vars.get(0), vars.get(1)));
                return true;
            }
        };
    }

}
