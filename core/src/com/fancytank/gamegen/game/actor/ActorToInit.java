package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.HashMap;
import java.util.LinkedList;

abstract class ActorToInit {
    LinkedList<ExecutableProducer> actionPerTick;
    LinkedList<ExecutableProducer> actionListeners;
    String name;
    String textureName;

    private static String[] textureNames = new String[]{"block_bounds_full", "block_clear_full", "block_heart_cutout", "block_star_cutout", "block_striped_full"};
    private static HashMap<String, Texture> textures;

    ActorToInit(String name, String textureName) {
        this.name = name;
        this.textureName = textureName;
        actionPerTick = new LinkedList<>();
        actionListeners = new LinkedList<>();
    }

    abstract BaseActor createInstance(int x, int y);

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
