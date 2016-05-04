package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

public class BlockActor extends Actor {
    Color tint;
    BlockShape shape;
    ArrayList<ConnectionArea> connectors;
    BlockAppearance blockAppearance;
    Rectangle boundingBox;
    private float touchedX, touchedY;
    private static ArrayList<BlockActor> list = new ArrayList<BlockActor>();

    public BlockActor(BlockShape shape, Color tint) {
        this.tint = tint;
        this.shape = shape;
        blockAppearance = new BlockAppearance(this, "le Placeholder");
        boundingBox = new Rectangle((int) getX(), (int) getY(), (int) blockAppearance.getWidth(), (int) blockAppearance.getHeight());
        connectors = ConnectionPlacer.getConnectors(this);
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
                        checkOverlapping(blockActor);
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - touchedX, y - touchedY);
            }
        });
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        for (ConnectionArea connectionArea : connectors)
            connectionArea.translate(deltaX, deltaY);
        boundingBox.setPosition(boundingBox.getX() + deltaX, boundingBox.getY() + deltaY);
        blockAppearance.translate(deltaX, deltaY);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        blockAppearance.drawShape(batch, alpha);
    }

    private void checkOverlapping(BlockActor blockActor) {
        if (this.boundingBox.overlaps(blockActor.boundingBox)) {
            checkConnectors(blockActor);
        }
    }

    private void checkConnectors(BlockActor collidable) {
        for (ConnectionArea homeConnector : connectors)
            for (ConnectionArea checkedConnector : collidable.connectors)
                if (homeConnector.getBoundingBox().overlaps(checkedConnector.getBoundingBox()))
                    System.out.println("Collision ! " + homeConnector + " + " + checkedConnector);
    }
}
