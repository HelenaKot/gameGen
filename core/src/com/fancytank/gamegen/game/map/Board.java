package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.TileType;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    public TileType[][] board;
    private static final long serialVersionUID = 1243613063057895682L;

    public Board() {
        board = new TileType[Constant.MAP_WIDTH][Constant.MAP_HEIGHT];
    }

    public Board(TileType initTile) {
        board = new TileType[Constant.MAP_WIDTH][Constant.MAP_HEIGHT];
        for (int row = 0; row < board.length; row++)
            Arrays.fill(board[row], initTile);
    }
}
