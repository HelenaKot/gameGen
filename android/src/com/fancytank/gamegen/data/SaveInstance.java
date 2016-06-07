package com.fancytank.gamegen.data;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.util.GregorianCalendar;

public class SaveInstance {
    public ProgrammingBlockSavedInstance[] blocks;
    public String fileName;
    public GregorianCalendar time;

    SaveInstance(String fileName, ProgrammingBlockSavedInstance[] blocks) {
        this.fileName = fileName;
        this.blocks = blocks;
        this.time = new GregorianCalendar();
    }
}