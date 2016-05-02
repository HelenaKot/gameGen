package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class BlockTextureManager {
    static TextureAtlas textureAtlas;
    static NinePatch plain[], connected[];

    public BlockTextureManager(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        init();
    }

    private static void init() {
        plain = new NinePatch[]{
                textureAtlas.createPatch("top_plain"),
                textureAtlas.createPatch("right_plain"),
                textureAtlas.createPatch("bottom_plain"),
                textureAtlas.createPatch("left_plain")
        };
        connected = new NinePatch[]{
                textureAtlas.createPatch("top_connection"),
                textureAtlas.createPatch("right_connection"),
                textureAtlas.createPatch("bottom_connection"),
                textureAtlas.createPatch("left_connection")
        };
    }
}
