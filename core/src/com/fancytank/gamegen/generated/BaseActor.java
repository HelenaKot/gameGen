package com.fancytank.gamegen.generated;

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
    static LinkedList<FloatConsumer> actPerTick = new LinkedList<FloatConsumer>();
    static LinkedList<InputListener> actionListeners = new LinkedList<InputListener>();

    public BaseActor(String textureName, String colorHexValue) {
        texture = getTexture(textureName);
        tint = getColor(colorHexValue);

        //TODO najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        for (InputListener il : actionListeners)
            addListener(il);
    }

    static Texture getTexture(String textureName) {
        return new Texture(Gdx.files.internal(textureName));
    }

    static Color getColor(String hexValue) {
        return Color.valueOf(hexValue);
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
