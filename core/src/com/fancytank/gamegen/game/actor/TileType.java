package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.HashMap;

public class TileType implements Serializable {
    String name;
    String textureName;
    private static final long serialVersionUID = 1233613063064495682L;

    private transient static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private transient static HashMap<String, Texture> textures;

    TileType(String name, String textureName) {
        this.name = name;
        this.textureName = textureName;
    }

    public Texture getTexture() {
        if (textures == null)
            initTextures();
        return textures.get(textureName);
    }

    private static void initTextures() {
        textures = new HashMap<>();
        for (String name : textureNames)
            textures.put(name, new Texture(Gdx.files.internal(name + ".png")));
    }
}
