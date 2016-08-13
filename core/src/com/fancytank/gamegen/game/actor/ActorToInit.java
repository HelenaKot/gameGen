package com.fancytank.gamegen.game.actor;

import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.LinkedList;

abstract class ActorToInit {
    LinkedList<ExecutableProducer> actionPerTick;
    LinkedList<ExecutableProducer> actionListeners;

    ActorToInit() {
        actionPerTick = new LinkedList<>();
        actionListeners = new LinkedList<>();
    }

    abstract BaseActor createInstance(int x, int y);
}