package com.fancytank.gamegen.data;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class SaveInstance implements Serializable {
    public ProgrammingBlockSavedInstance[] blocks;
    public String fileName;
    public GregorianCalendar time;

    SaveInstance(String fileName, ProgrammingBlockSavedInstance[] blocks) {
        this.fileName = fileName;
        this.blocks = blocks;
        this.time = new GregorianCalendar();
    }
}