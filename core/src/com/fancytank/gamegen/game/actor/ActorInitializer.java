package com.fancytank.gamegen.game.actor;

import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.HashMap;
import java.util.LinkedList;

public class ActorInitializer {
    HashMap<String, ActorToInit> actorToInit;
    static ActorInitializer instance;

    public ActorInitializer() {
        if (instance == null) {
            actorToInit = new HashMap<>();
            actorToInit.put("unspecified", new ActorToInit("unspecified", null, "#ffffff") {
                BaseActor createInstance(int x, int y) {
                    return new EmptyActor(x, y, "unspecified");
                }
            }); // my "nullobject"
            actorToInit.put("empty", new ActorToInit("empty", null, "#ffffff") {
                BaseActor createInstance(int x, int y) {
                    return new EmptyActor(x, y, "empty");
                }
            });
            actorToInit.put("generic", new ActorToInit("generic", "block_bounds_full", "#ffffff") {
                BaseActor createInstance(int x, int y) {
                    return new GenericActor(x, y, "generic");
                }
            });
            instance = this;
        }
    }

    public static String[] getActorNames() {
        LinkedList<String> names = new LinkedList<>();
        for (String key : instance.actorToInit.keySet())
            if (!key.equals("unspecified"))
                names.add(key);
        return names.toArray(new String[names.size()]);
    }

    public static void addActorClass(String name, String textureName, String defaultColorHex) {
        instance.actorToInit.put(name, new CustomActorToInit(name, textureName, defaultColorHex));
    }

    public static void addActorClass(CustomActorToInit actorToInit) {
        instance.actorToInit.put(actorToInit.tile.name, actorToInit);
    }

    public static LinkedList<ExecutableProducer> getListenerList(String name) {
        return instance.actorToInit.get(name).actionListeners;
    }

    public static LinkedList<ExecutableProducer> getActionsPerTick(String name) {
        return instance.actorToInit.get(name).actionPerTick;
    }

    public static BaseActor getInstanceOf(String name, int x, int y) {
        if (instance.actorToInit.containsKey(name))
            return instance.actorToInit.get(name).createInstance(x, y);
        else System.out.println("Actor with this name can not be initialized = " + name);
        return instance.actorToInit.get("unspecified").createInstance(x, y);
    }

    public static void addActionListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionListeners.add(executable);
    }

    public static void addTimerListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionPerTick.add(executable);
    }

    public static LinkedList<TileType> getCustomActors() {
        LinkedList<TileType> output = new LinkedList<>();
        for (ActorToInit actor : instance.actorToInit.values())
            if (actor instanceof CustomActorToInit)
                output.add(actor.tile);
        return output;
    }

    public static TileType getActorTile(String name) {
        if (instance.actorToInit.containsKey(name))
            return instance.actorToInit.get(name).tile;
        return instance.actorToInit.get("unspecified").tile;
    }

    public static void dispose() {
        instance = null;
    }
}
