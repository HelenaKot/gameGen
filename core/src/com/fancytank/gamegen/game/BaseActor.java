package com.fancytank.gamegen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.LinkedList;

public abstract class BaseActor extends Actor {
    Texture texture;
    Color tint;
    public int x, y;
    static LinkedList<FloatConsumer> actPerTick = new LinkedList<FloatConsumer>();
    static LinkedList<InputListener> actionListeners = new LinkedList<InputListener>();

    public BaseActor(Color tint) {
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        this.tint = tint;

        //TODO najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        for (InputListener il : actionListeners)
            addListener(il);
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
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
