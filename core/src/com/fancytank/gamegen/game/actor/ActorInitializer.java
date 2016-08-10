package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.HashMap;
import java.util.LinkedList;

public class ActorInitializer {
    HashMap<String, ActorToInit> actorToInit;
    static ActorInitializer instance;

    public ActorInitializer() {
        if (instance == null) {
            actorToInit = new HashMap<>();
            actorToInit.put("unspecified", new ActorToInit() {
                BaseActor createInstance(int x, int y) {
                    return new EmptyActor(x, y, "unspecified");
                }
            }); // my "nullobject"
            actorToInit.put("empty", new ActorToInit() {
                BaseActor createInstance(int x, int y) {
                    return new EmptyActor(x, y, "empty");
                }
            });
            actorToInit.put("generic", new ActorToInit() {
                BaseActor createInstance(int x, int y) {
                    return new GenericActor(x, y, "generic");
                }
            });
            instance = this;
        }
    }

    static abstract class ActorToInit {
        LinkedList<ExecutableProducer> actionPerTick;
        LinkedList<ExecutableProducer> actionListeners;

        ActorToInit() {
            actionPerTick = new LinkedList<>();
            actionListeners = new LinkedList<>();
        }

        abstract BaseActor createInstance(int x, int y);
    }

    public static String[] getActorNames() {
        LinkedList<String> names = new LinkedList<>();
        for (String key : instance.actorToInit.keySet())
            if (!key.equals("unspecified"))
                names.add(key);
        return names.toArray(new String[names.size()]);
    }

    public static void addActorClass(String name, Texture texture) {
        instance.actorToInit.put(name, new ActorToInit() {
            BaseActor createInstance(int x, int y) {
                GenericActor myActor = new GenericActor(x, y, name);
                myActor.texture = texture;
                return myActor
            }
        });
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
        return null;
    }

    public static void addActionListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionListeners.add(executable);
    }

    public static void addTimerListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionPerTick.add(executable);
    }

}
