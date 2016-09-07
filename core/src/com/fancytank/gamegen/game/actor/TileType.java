package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.HashMap;

public class TileType implements Serializable {
    public String name;
    public String textureName;
    String colorHex;
    private static final long serialVersionUID = 1233613063064495682L;

    private transient static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private transient static HashMap<String, Texture> textures;

    public TileType(String name, String textureName, String colorHex) {
        this.name = name;
        this.textureName = textureName;
        if (colorHex.length() > 7) //alpha saved
            this.colorHex = colorHex.substring(0, 6).toLowerCase();
        else
            this.colorHex = colorHex.toLowerCase();
    }

    public Texture getTexture() {
        return textures.get(textureName);
    }

    public Color getColor() {
        if (colorHex.length() > 7)
            //return Color.valueOf("#"+colorHex.substring(2,8));
        return Color.valueOf(colorHex.substring(0, 6).toLowerCase());
        return Color.valueOf(colorHex);
    }

    public int getIntColor() {
        int color = Integer.parseInt(colorHex.substring(1), 16);
        color |= 0x00000000ff000000;
        return color;
    }

    public static void initTextures() {
        textures = new HashMap<>();
        for (String name : textureNames)
            textures.put(name, new Texture(Gdx.files.internal(name + ".png")));
    }
}
