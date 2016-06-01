package com.fancytank.gamegen.game.actor;

import com.fancytank.gamegen.game.script.Executable;
import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.HashMap;
import java.util.LinkedList;

public class ActorInitializer {
    HashMap<String, ActorToInit> actorToInit;
    static ActorInitializer instance;

    public ActorInitializer() {
        if (instance == null) {
            actorToInit = new HashMap<String, ActorToInit>();
            actorToInit.put("unspecified", new ActorToInit(EmptyActor.class)); // my "nullobject"
            actorToInit.put("empty", new ActorToInit(EmptyActor.class));
            actorToInit.put("generic", new ActorToInit(GenericActor.class));
            instance = this;
        }
    }

    class ActorToInit {
        Class<? extends BaseActor> actorClass;
        LinkedList<Executable> actPerTick;
        LinkedList<ExecutableProducer> actionListeners;

        ActorToInit(Class<? extends BaseActor> actorClass) {
            this.actorClass = actorClass;
            actPerTick = new LinkedList<Executable>();
            actionListeners = new LinkedList<ExecutableProducer>();
        }
    }

    public static String[] getActorNames() {
        return (String[]) instance.actorToInit.keySet().toArray();
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

    public static LinkedList<Executable> getActionsPerTick(String name) {
        return instance.actorToInit.get(name).actPerTick;
    }

    public static BaseActor getInstanceOf(String name, int x, int y) {
        try {
            return instance.actorToInit.get(name).actorClass
                    .getConstructor(int.class, int.class).newInstance(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addActionListener(String className, ExecutableProducer executable) {
        instance.actorToInit.get(className).actionListeners.add(executable);
    }

}
