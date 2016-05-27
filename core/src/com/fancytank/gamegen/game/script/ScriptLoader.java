package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

public class ScriptLoader {
    public static void load(ProgrammingBlockSavedInstance[] data) {
        System.out.println("load debug");
        for (ProgrammingBlockSavedInstance a : data)
            System.out.println(a);
    }
}
