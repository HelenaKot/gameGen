package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.AndroidGameGenerator;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;

import java.util.ArrayList;
import java.util.UUID;

import static com.fancytank.gamegen.programming.looks.ConnectionPlacer.getConnectors;

public class ProgrammingBlock extends Group {
    CoreBlock coreBlock;
    ArrayList<ConnectionArea> connectors;
    private float touchedX, touchedY;
    private static ArrayList<ProgrammingBlock> blocksList = new ArrayList<ProgrammingBlock>();

    public ProgrammingBlock(BlockData data, Color tint) {
        setName(UUID.randomUUID().toString());
        coreBlock = new CoreBlock(this, data, tint);
        connectors = getConnectors(coreBlock);
        blocksList.add(this);
        setBounds(0, 0, coreBlock.getWidth(), coreBlock.getHeight());

        setUpListeners();
        populateGroup();

        AndroidGameGenerator.addToStage(this);
    }

    InputListener inputListener;

    private void setUpListeners() {
        final ProgrammingBlock local = this;
        addListener(inputListener = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedX = x;
                touchedY = y;
                return detach();
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (ProgrammingBlock programmingBlock : blocksList)
                    if (programmingBlock != local)
                        checkOverlapping(programmingBlock);
                detachingBlock = null;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (detachingBlock != null) {
                    detachingBlock.moveBy( x - detachingBlock.getX() + getX() - detachingBlock.touchedX, // + detachingX - touchedX - detachingBlock.touchedX,
                            y - detachingBlock.getY() + getY() - detachingBlock.touchedY );//+ detachingY - touchedY - detachingBlock.touchedY);
                } else {
                    moveBy(x - touchedX, y - touchedY);
                }
            }
        });
    }

    private void populateGroup() {
        this.addActor(coreBlock);
        for (ConnectionArea connectionArea : connectors)
            this.addActor(connectionArea);
    }

    private void checkOverlapping(ProgrammingBlock programmingBlock) {
        if (coreBlock.getBoundingBox().overlaps(programmingBlock.coreBlock.getBoundingBox())) {
            checkConnectors(programmingBlock);
        }
    }

    private void checkConnectors(ProgrammingBlock programmingBlock) {
        for (ConnectionArea localConnector : connectors)
            for (ConnectionArea dockingConnector : programmingBlock.connectors)
                if (localConnector.getBoundingBox().overlaps(dockingConnector.getBoundingBox()))
                    if (localConnector.direction == dockingConnector.direction.flip())
                        tryAttach(localConnector, dockingConnector);
    }

    private Group attachedTo = null;
    // private static int compensation = -(int) (BlockAppearance.padding * 0.8);

    void tryAttach(ConnectionArea localConnector, ConnectionArea dockingConnector) {
        ProgrammingBlock block = getProgrammingBlock(dockingConnector);
        if (attachedTo == null && findActor(block.getName()) == null) {
            if (this.getSignificance() < block.getSignificance())
                attachBlock(localConnector, dockingConnector);
            else
                attachBlock(dockingConnector, localConnector);
        }
    }

    private int getSignificance() {
        return this.coreBlock.data.shape.significance;
    }

    private static ProgrammingBlock getProgrammingBlock(ConnectionArea connectionArea) {
        return connectionArea.coreBlock.getParent();
    }

    static void attachBlock(ConnectionArea dockingConnector, ConnectionArea baseConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(dockingConnector), baseBlock = getProgrammingBlock(baseConnector);
        attachingBlock.attachedTo = baseBlock;
        attachingBlock.setPosition(baseConnector.getX() * 2 - dockingConnector.getX() * 2,
                baseConnector.getY() * 2 - dockingConnector.getY() * 2);
        baseBlock.addActor(attachingBlock);
    }

    private static ProgrammingBlock detachingBlock;

    boolean detach() {
        if (attachedTo != null) {
            attachedTo.removeActor(this);
            this.setPosition(attachedTo.getX() + this.getX(), attachedTo.getY() + this.getY());
            attachedTo = null;
            AndroidGameGenerator.addToStage(this);
            detachingBlock = this;
            return false;
        }
        return true;
    }
}
