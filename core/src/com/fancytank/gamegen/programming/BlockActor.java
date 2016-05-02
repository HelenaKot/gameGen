package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.LinkedList;

public class BlockActor extends Actor implements Collidable {
    Color tint;
    private BlockAppearance blockAppearance;
    private float touchedX, touchedY;
    private static LinkedList<BlockActor> list = new LinkedList<BlockActor>();
    private Rectangle boundingBox;

    public BlockActor(BlockShape shape, Color tint) {
        this.tint = tint;
        blockAppearance = new BlockAppearance(this, "le Placeholder", shape);
        boundingBox = new Rectangle((int) getX(), (int) getY(), (int) getHeight(), (int) getWidth());
        list.add(this);

        final BlockActor self = this;

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedX = x;
                touchedY = y;
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (BlockActor blockActor : list)
                    if (blockActor != self)
                        isOverlapping(blockActor);
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - touchedX, y - touchedY);
            }
        });
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        boundingBox.setPosition(boundingBox.getX() + deltaX, boundingBox.getY() + deltaY);
        blockAppearance.translate(deltaX, deltaY);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        blockAppearance.drawShape(batch, alpha);
    }

    @Override
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void isOverlapping(Collidable collidable) {
        if (collidable.getBoundingBox().overlaps(boundingBox)) {
            //TODO if blocks overlaps, check for tiny collidable elements.
            System.out.println("Collision ! " + collidable);
        }
    }
}
