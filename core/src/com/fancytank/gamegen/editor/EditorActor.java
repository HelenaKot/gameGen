package com.fancytank.gamegen.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.actor.ActorInitializer;

public class EditorActor extends Actor {
    int x, y;
    String className;
    Color tint;
    Texture texture;

    private static String brushClass;
    private static Color brushColor;
    private static Texture brushTexture;
    private static Texture defaultTexture;

    public EditorActor(int x, int y) {
        this.x = x;
        this.y = y;
        tint = Color.WHITE;
        if (defaultTexture == null)
            defaultTexture = new Texture(Gdx.files.internal("block_star_cutout.png"));
        texture = defaultTexture;
        setBounds(getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        addListener(classSwapListener());
    }

    public void setProperties(String className, Color tint, Texture texture) {
        this.texture = texture;
        this.tint = tint;
        this.className = className;
    }

    public void draw(Batch batch, float alpha) {
        if (texture != null) {
            batch.setColor(tint);
            batch.draw(texture, this.getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        }
    }

    public static void setBrush(String className, Color tint) {
        brushClass = className;
        brushColor = tint;
        brushTexture = ActorInitializer.getActorTexture(className);
    }

    private InputListener classSwapListener() {
        return new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setProperties(brushClass, brushColor, brushTexture);
                return true;
            }
        };
    }
}
