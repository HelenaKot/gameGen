package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.Arrays;

public class CustomActorToInit extends ActorToInit implements Serializable {
    private static final long serialVersionUID = 1233613063064495682L;
    String name;
    String textureName;
    private static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private static Texture[] textures;

    CustomActorToInit(String name, String textureName) {
        super();
        this.name = name;
        this.textureName = textureName;
    }

    private static void loadTextures() {
        textures = new Texture[]{
                new Texture(Gdx.files.internal("block_bounds_full.png")),
                new Texture(Gdx.files.internal("block_clear_full.png")),
                new Texture(Gdx.files.internal("block_heart_cutout.png")),
                new Texture(Gdx.files.internal("block_star_cutout.png")),
                new Texture(Gdx.files.internal("block_striped_full.png"))
        };
    }

    @Override
    BaseActor createInstance(int x, int y) {
        if (textures == null)
            loadTextures();

        return new GenericActor(x, y, name, textures[Arrays.asList(textureNames).indexOf(textureName)]);
    }
}
