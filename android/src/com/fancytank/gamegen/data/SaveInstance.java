package com.fancytank.gamegen.data;

import com.fancytank.gamegen.game.actor.CustomActorToInit;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.io.Serializable;

public class SaveInstance implements Serializable {
    private static final long serialVersionUID = 1233613063064496930L;
    public ProgrammingBlockSavedInstance[] blocks;
    public CustomActorToInit[] tiles;
    public String fileName;

    SaveInstance(String fileName, ProgrammingBlockSavedInstance[] blocks, CustomActorToInit[] tiles) {
        this.fileName = fileName;
        this.blocks = blocks;
        this.tiles = tiles;
    }
}