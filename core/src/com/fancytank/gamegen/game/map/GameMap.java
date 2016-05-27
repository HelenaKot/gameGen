package com.fancytank.gamegen.game.map;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.BaseActor;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.GenericActor;

public class GameMap {
    int widthPadding, heightOffset;
    BaseActor[][] map;

    public GameMap(int width, int height, int heightOffset) {
        this.heightOffset = heightOffset;
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(width, height);
    }

    private void initEmptyMap(int width, int height) {
        map = new BaseActor[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                initActor(new GenericActor(Color.LIGHT_GRAY, x, y));
            }
        }
    }

    void changeBlock(BaseActor actor) {
        map[actor.x][actor.y].remove();
        initActor(actor);
    }

    private void initActor(BaseActor block) {
        map[block.x][block.y] = block;
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, (block.y + heightOffset) * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }
}
