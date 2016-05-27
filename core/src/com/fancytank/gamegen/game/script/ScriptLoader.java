package com.fancytank.gamegen.game.script;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.looks.BlockShape;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        System.out.println("load debug");
        for (ProgrammingBlockSavedInstance a : data)
            System.out.println(a);
        loadListeners(data);
    }

    private static void loadListeners(ProgrammingBlockSavedInstance[] data) {
        for (ProgrammingBlockSavedInstance savedBlock : data)
            if (savedBlock.data.shape == BlockShape.ENCLOSED) //todo na razie placeholder, potem powinnam dać data tag
            {
                //todo na razie ostatni input to blok, na którym ma być wykonywana czynność, to potem też do metody
                InputFragment targetInput = savedBlock.data.getInputs()[savedBlock.data.getInputs().length-1];
                BlockData targetValue = targetInput.connectedTo;
                ActorInitializer.addActionListener(targetValue.getValue(), new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("PYK");
                        return true;
                    }
                } );
            }
    }


}
