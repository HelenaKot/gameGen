package com.fancytank.gamegen.game.map;

import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.BaseActor;

public class GameMap {
    int widthPadding, heightOffset;
    BaseActor[][] map;

    GameMap(int width, int height, int heightOffset) {
        this.heightOffset = heightOffset;
        widthPadding = Constant.MAP_PADDING;
        if (map == null)
            initEmptyMap(width, height);
    }

    private void initEmptyMap(int width, int height) {
        map = new BaseActor[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                initActor(ActorInitializer.getInstanceOf("empty", x, y));
        changeBlock(ActorInitializer.getInstanceOf("generic", 2, 2));
    }

    void changeBlock(BaseActor actor) {
        if (0 <= actor.x && actor.x < map.length && 0 <= actor.y && actor.y < map[0].length) {
            map[actor.x][actor.y].remove();
            initActor(actor);
        }
    }

    private void initActor(BaseActor block) {
        map[block.x][block.y] = block;
        map[block.x][block.y].setPosition(block.x * Constant.BLOCK_SIZE + widthPadding, (block.y + heightOffset) * Constant.BLOCK_SIZE);
        MainGdx.addToStage(block);
    }
}
