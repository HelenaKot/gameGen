package com.fancytank.gamegen.data;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.fancytank.gamegen.programming.data.VariableList;

import java.io.Serializable;

public class SaveInstance implements Serializable {
    private static final long serialVersionUID = 1233613063064496930L;
    public ProgrammingBlockSavedInstance[] blocks;
    public String fileName;

    SaveInstance(String fileName, ProgrammingBlockSavedInstance[] blocks) {
        this.fileName = fileName;
        this.blocks = blocks;
    }
}