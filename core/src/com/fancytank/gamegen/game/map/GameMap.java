package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.TileType;

public class GameMap implements MapType {
    int widthPadding;
    private BaseActor[][] map;

    @Override
    public MapType init() {
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(Constant.MAP_WIDTH, Constant.MAP_HEIGHT);
        return this;
    }

    @Override
    public void setBoard(Board mapBoard) {
        clearMap();
        TileType[][] board = mapBoard.board;
        map = new BaseActor[Constant.MAP_WIDTH][Constant.MAP_WIDTH];
        for (int x = 0; x < Constant.MAP_WIDTH; x++)
            for (int y = 0; y < Constant.MAP_HEIGHT; y++)
                initActor(ActorInitializer.getInstanceOf(board[x][y].name, x, y));
    }

    private void clearMap() {
        for (int x = 0; x < Constant.MAP_WIDTH; x++)
            for (int y = 0; y < Constant.MAP_HEIGHT; y++)
                map[x][y].remove();
    }

    private void initEmptyMap(int width, int height) {
        map = new BaseActor[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                initActor(ActorInitializer.getInstanceOf("empty", x, y));
    }

    @Override
    public void changeBlock(BaseActor actor) {
        if (actor == null)
            System.out.println(actor + " is not initialized");
        else if (inBounds(actor.x, actor.y) && map[actor.x][actor.y].getClassName() != actor.getClassName()) {
            map[actor.x][actor.y].remove();
            initActor(actor);
        }
    }

    private boolean inBounds(int x, int y) {
        return 0 <= x && x < Constant.MAP_WIDTH && 0 <= y && y < Constant.MAP_HEIGHT;
    }

    @Override
    public BaseActor[][] getMap() {
        return map;
    }

    private void initActor(BaseActor block) {
        map[block.x][block.y] = block;
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, block.y * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }
}
