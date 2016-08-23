package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

public class TileType implements Serializable {
    public String name;
    public String textureName;
    public String colorHex;
    public transient Color tint;
    private static final long serialVersionUID = 1233613063064495682L;

    private transient static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private transient static HashMap<String, Texture> textures;

    public TileType(String name, String textureName, String colorHex) {
        this.name = name;
        this.textureName = textureName;
        setColor(colorHex);
    }

    public Texture getTexture() {
        if (textures == null)
            initTextures();
        return textures.get(textureName);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        setColor(colorHex);
    }

    private static void initTextures() {
        textures = new HashMap<>();
        for (String name : textureNames)
            textures.put(name, new Texture(Gdx.files.internal(name + ".png")));
    }

    private void setColor(String colorHex) {
        this.colorHex = colorHex;
        if (colorHex != null)
            tint = Color.valueOf(colorHex);
    }
}
