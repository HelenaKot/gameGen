package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.AndroidGameGenerator;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
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

    private void setUpListeners() {
        final ProgrammingBlock local = this;
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedX = x;
                touchedY = y;
                detach();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (ProgrammingBlock programmingBlock : blocksList)
                    if (programmingBlock != local)
                        checkOverlapping(programmingBlock);
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - touchedX, y - touchedY);
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
            System.out.println("block touching");
            checkConnectors(programmingBlock);
        }
    }

    private void checkConnectors(ProgrammingBlock programmingBlock) {
        for (ConnectionArea localConnector : connectors)
            for (ConnectionArea dockingConnector : programmingBlock.connectors)
                if (localConnector.getBoundingBox().overlaps(dockingConnector.getBoundingBox()))
                    if (localConnector.direction == dockingConnector.direction.flip())
                        tryAttach(dockingConnector);
    }

    //todo rework basing on some hierarchy of blocks.
    private Group attachedTo = null;
    private static int compensation = -(int) (BlockAppearance.padding * 0.8);

    void tryAttach(ConnectionArea dockingConnector) {
        ProgrammingBlock block = dockingConnector.coreBlock.getParent();
        if (attachedTo == null && findActor(block.getName()) == null) {
            if (this.getSignificance() > block.getSignificance())
                attachBlock(this, block, dockingConnector);
            else
                attachBlock(block, this, dockingConnector);
        }
    }

    private int getSignificance() {
        return this.coreBlock.data.shape.significance;
    }

    static void attachBlock(ProgrammingBlock baseBlock, ProgrammingBlock attachingBlock, ConnectionArea dockingConnector) {
        attachingBlock.attachedTo = baseBlock;
        attachingBlock.setPosition(dockingConnector.direction.deltaX * (dockingConnector.coreBlock.getWidth() + compensation),
                dockingConnector.direction.deltaY * (dockingConnector.coreBlock.getHeight() + compensation));
        baseBlock.addActor(attachingBlock);
    }

    void detach() {
        if (attachedTo != null) {
            System.out.println("Detaching");
            attachedTo.removeActor(this);
            moveBy(100, 100); //todo some better shit
            attachedTo = null;
            AndroidGameGenerator.addToStage(this);
        }
    }
}
