package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.HashMap;

public class TileType implements Serializable {
    public String name;
    public String textureName;
    public String colorHex;
    private static final long serialVersionUID = 1233613063064495682L;

    private transient static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private transient static HashMap<String, Texture> textures;

    public TileType(String name, String textureName, String colorHex) {
        this.name = name;
        this.textureName = textureName;
        this.colorHex = colorHex.toLowerCase();
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
