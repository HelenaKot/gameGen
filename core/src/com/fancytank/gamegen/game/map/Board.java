package com.fancytank.gamegen.game.map;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    String[][] board;
    private static final long serialVersionUID = 1243613063057895682L;

    public Board(int width, int height, String initId) {
        //TODO i dont know how i want to do it yet
        board = new String[width][height];
        Arrays.fill(board, initId);
    }
}
