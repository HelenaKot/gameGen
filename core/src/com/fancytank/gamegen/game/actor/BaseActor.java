package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.FloatConsumer;

import java.util.LinkedList;

public abstract class BaseActor extends Actor {
    public int x, y;

    /**
     * najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
     */
    public BaseActor(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        for (InputListener il : getListenerList())
            addListener(il);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (FloatConsumer ic : getActionsPerTick()) {
            ic.accept(delta);
        }
    }

    public LinkedList<InputListener> getListenerList() {
        return ActorInitializer.getListenerList(getClassName());
    }

    private LinkedList<FloatConsumer> myActionsPerTick;

    public LinkedList<FloatConsumer> getActionsPerTick() {
        if (myActionsPerTick == null)
            myActionsPerTick = ActorInitializer.getActionsPerTick(getClassName());
        return myActionsPerTick;
    }

    private String className;

    public String getClassName() {
        if (className == null)
            className = ActorInitializer.askClassName(this.getClass());
        return className;
    }
}
