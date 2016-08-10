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
            actorToInit.put("unspecified", new ActorToInit(EmptyActor.class)); // my "nullobject"
            actorToInit.put("empty", new ActorToInit(EmptyActor.class));
            actorToInit.put("generic", new ActorToInit(GenericActor.class));
            instance = this;
        }
    }

    static class ActorToInit {
        Class<? extends BaseActor> actorClass;
        LinkedList<ExecutableProducer> actionPerTick;
        LinkedList<ExecutableProducer> actionListeners;

        ActorToInit(Class<? extends BaseActor> actorClass) {
            this.actorClass = actorClass;
            actionPerTick = new LinkedList<>();
            actionListeners = new LinkedList<>();
        }
    }

    public static String[] getActorNames() {
        LinkedList<String> names = new LinkedList<>();
        for (String key : instance.actorToInit.keySet())
            if (!key.equals("unspecified"))
                names.add(key);
        return names.toArray(new String[names.size()]);
    }

    public static void addActorClass(String name, Class<? extends BaseActor> tileClass) {
        instance.actorToInit.put(name, new ActorToInit(tileClass));
    }

    public static String askClassName(Class<? extends BaseActor> myClass) {
        for (String name : instance.actorToInit.keySet())
            if (myClass.equals(instance.actorToInit.get(name).actorClass))
                return name;
        return "unspecified";
    }

    public static LinkedList<ExecutableProducer> getListenerList(String name) {
        return instance.actorToInit.get(name).actionListeners;
    }

    public static LinkedList<ExecutableProducer> getActionsPerTick(String name) {
        return instance.actorToInit.get(name).actionPerTick;
    }

    public static BaseActor getInstanceOf(String name, int x, int y) {
        try {
            if (instance.actorToInit.containsKey(name))
                return instance.actorToInit.get(name).actorClass.getConstructor(int.class, int.class).newInstance(x, y);
            else System.out.println("Actor with this name can not be initialized = " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addActionListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionListeners.add(executable);
    }

    public static void addTimerListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionPerTick.add(executable);
    }

}
