package com.fancytank.gamegen;

import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class SaveInstance implements Serializable {
    private static final long serialVersionUID = 1233613063064496930L;
    public ProgrammingBlockSavedInstance[] blocks;
    public LinkedList<TileType> tiles;
    public HashMap<String, Board> boards;
    public String fileName;

    public SaveInstance(String fileName, ProgrammingBlockSavedInstance[] blocks, LinkedList<TileType> tiles, HashMap<String, Board> boards) {
        this.fileName = fileName;
        this.blocks = blocks;
        this.tiles = tiles;
        this.boards = boards;
    }
}