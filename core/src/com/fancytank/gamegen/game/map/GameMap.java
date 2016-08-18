package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.TileType;

public class GameMap implements MapType {
    int widthPadding, heightOffset;
    private BaseActor[][] map;

    @Override
    public MapType init(int width, int height, int heightOffset) {
        this.heightOffset = heightOffset;
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(width, height);
        return this;
    }

    public MapType initFromBoard(int width, int height, int heightOffset, Board board) {
        initMapFromBoard(board);
        return init(width, height, heightOffset);
    }

    private void initMapFromBoard(Board mapBoard) {
        TileType[][] board = mapBoard.board;
        map = new BaseActor[mapBoard.width][mapBoard.height];
        for (int x = 0; x < mapBoard.width; x++)
            for (int y = 0; y < mapBoard.height; y++)
                initActor(ActorInitializer.getInstanceOf(board[x][y].name, x, y));
    }

    private void initEmptyMap(int width, int height) {
        map = new BaseActor[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                initActor(ActorInitializer.getInstanceOf("empty", x, y));
        changeBlock(ActorInitializer.getInstanceOf("generic", 2, 2));
    }

    @Override
    public void changeBlock(BaseActor actor) {
        if (actor == null)
            System.out.println(actor + " is not initialized");
        else if (0 <= actor.x && actor.x < map.length && 0 <= actor.y && actor.y < map[0].length) {
            map[actor.x][actor.y].remove();
            initActor(actor);
        }
    }

    @Override
    public BaseActor[][] getMap() {
        return map;
    }

    private void initActor(BaseActor block) {
        map[block.x][block.y] = block;
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, (block.y + heightOffset) * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }
}
