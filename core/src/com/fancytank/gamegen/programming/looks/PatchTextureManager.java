package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fancytank.gamegen.programming.Direction;

public final class PatchTextureManager {
    static TextureAtlas textureAtlas;
    private static NinePatch mini[], plain[], connected[], socket;

    public PatchTextureManager(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        init();
    }

    private static void init() {
        mini = new NinePatch[]{
                textureAtlas.createPatch("top_mini"),
                textureAtlas.createPatch("left_connection"),
                textureAtlas.createPatch("bottom_mini"),
                textureAtlas.createPatch("right_plain")};
        plain = new NinePatch[]{
                textureAtlas.createPatch("top_plain"),
                textureAtlas.createPatch("left_plain"),
                textureAtlas.createPatch("bottom_plain"),
                textureAtlas.createPatch("right_plain")
        };
        connected = new NinePatch[]{
                textureAtlas.createPatch("top_connection"),
                textureAtlas.createPatch("left_connection"),
                textureAtlas.createPatch("bottom_connection"),
                textureAtlas.createPatch("right_connection")
        };
        socket = new NinePatch(textureAtlas.createPatch("right_socket"));
    }

    public static NinePatch getPatch(boolean isConnected, Direction direction) {
        if (isConnected)
            return connected[direction.ordinal()];
        else
            return plain[direction.ordinal()];
    }

    public static NinePatch getMiniPatch(Direction direction){
        return mini[direction.ordinal()];
    }

    public static NinePatch getSocket() {
        return socket;
    }
}
