package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.actor.TileType;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    public TileType[][] board;
    public int width, height;
    private static final long serialVersionUID = 1243613063057895682L;

    public Board(int width, int height) {
        board = new TileType[this.width = width][this.height = height];
    }

    public Board(int width, int height, TileType initTile) {
        board = new TileType[this.width = width][this.height = height];
        for (int row = 0; row < board.length; row++)
            Arrays.fill(board[row], initTile);
    }
}
