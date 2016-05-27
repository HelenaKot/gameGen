package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.FloatConsumer;

import java.util.LinkedList;

public abstract class BaseActor extends Actor {
    public int x, y;
    static LinkedList<FloatConsumer> actPerTick = new LinkedList<FloatConsumer>();
    static LinkedList<InputListener> actionListeners = new LinkedList<InputListener>();

    /**
     * najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
     */
    public BaseActor(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        for (InputListener il : actionListeners)
            addListener(il);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (FloatConsumer ic : actPerTick) {
            ic.accept(delta);
        }
    }

    public static void addTickEvent(FloatConsumer floatConsumer) {
        actPerTick.add(floatConsumer);
    }

    public static void addActionListener(InputListener inputListener) {
        actionListeners.add(inputListener);
    }
}
