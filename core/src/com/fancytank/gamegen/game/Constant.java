package com.fancytank.gamegen.game;

public class Constant {
    public static int BLOCK_SIZE;
    public static int MAP_PADDING;
    public static int MAP_HEIGHT;
    public static int MAP_WIDTH;

    private static int BLOCKS_IN_ROW = 20;
    private static int BLOCK_SCALE;

    public static void setUpBlockConstants(int gameWidth, int gameHeight) {
        BLOCK_SCALE = (gameWidth / BLOCKS_IN_ROW);
        BLOCK_SIZE = BLOCK_SCALE;
        MAP_PADDING = (gameWidth - BLOCKS_IN_ROW * BLOCK_SIZE) / 2;
        MAP_WIDTH = gameWidth / Constant.BLOCK_SIZE;
        MAP_HEIGHT = gameHeight / Constant.BLOCK_SIZE;
    }
}