package com.fancytank.gamegen.editor;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.BaseActor;
import com.fancytank.gamegen.game.map.Board;
import com.fancytank.gamegen.game.map.MapType;

public class EditorMap implements MapType {
    int widthPadding, heightOffset;
    private EditorActor[][] map;

    @Override
    public MapType init(int width, int height, int heightOffset) {
        this.heightOffset = heightOffset;
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(width, height);
        return this;
    }

    private void initEmptyMap(int width, int height) {
        map = new EditorActor[width][height];
        EditorActor.setBrush("empty", Color.CORAL);
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
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, (block.y + heightOffset) * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }

    public Board getMapAsBoard() {
        return null;
    }
}
