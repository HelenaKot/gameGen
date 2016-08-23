package com.fancytank.gamegen.editor;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.MapType;

public class EditorMap implements MapType {
    int widthPadding;
    private EditorActor[][] map;

    @Override
    public MapType init() {
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(Constant.MAP_WIDTH, Constant.MAP_HEIGHT);
        return this;
    }

    @Override
    public void setBoard(Board mapBoard) {
        for (int x = 0; x < mapBoard.width; x++)
            for (int y = 0; y < mapBoard.height; y++) {
                TileType tile = mapBoard.board[x][y];
                map[x][y].setProperties(tile.name, tile.textureName, Color.valueOf(tile.colorHex), tile.getTexture());
            }
    }

    private void initEmptyMap(int width, int height) {
        map = new EditorActor[width][height];
        EditorActor.setBrush("empty");
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                initActor(new EditorActor(x, y));
    }

    @Override
    public void changeBlock(BaseActor actor) {
    }

    @Override
    public BaseActor[][] getMap() {
        return new BaseActor[0][0];
    }

    private void initActor(EditorActor block) {
        map[block.x][block.y] = block;
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, block.y * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }

    public Board getMapAsBoard() {
        int width = map.length, height = map[0].length;
        Board output = new Board(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                output.board[i][j] = new TileType(map[i][j].className, map[i][j].textureName, map[i][j].tint.toString());
        return output;
    }
}
