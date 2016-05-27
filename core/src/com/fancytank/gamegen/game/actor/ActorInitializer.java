package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.HashMap;

public class ActorInitializer {
    HashMap<String, Class<? extends BaseActor>> actors;
    static ActorInitializer instance;

    public ActorInitializer() {
        if (instance == null)
            init();
    }

    private void init() {
        if (instance == null) {
            actors = new HashMap<String, Class<? extends BaseActor>>();
            actors.put("empty", EmptyActor.class);
            actors.put("generic", GenericActor.class);
        }
        instance = this;
    }

    public static String[] getActorNames() {
        return (String[]) instance.actors.keySet().toArray();
    }

    public static BaseActor getInstanceOf(String name, int x, int y) {
        try {
            return instance.actors.get(name).getConstructor(int.class, int.class).newInstance(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addActionListener(String name, InputListener inputListener) {
        try {
            instance.actors.get(name).getMethod("addActionListener",InputListener.class).invoke(null, inputListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
