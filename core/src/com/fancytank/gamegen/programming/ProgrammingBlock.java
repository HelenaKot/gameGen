package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.AndroidGameGenerator;

import java.util.ArrayList;
import java.util.UUID;

public class ProgrammingBlock extends Group {
    CoreBlock coreBlock;
    ArrayList<ConnectionArea> connectors;
    private float touchedX, touchedY;
    private static ArrayList<ProgrammingBlock> blocksList = new ArrayList<ProgrammingBlock>();

    public ProgrammingBlock(BlockArrangement shape, Color tint) {
        setName(UUID.randomUUID().toString());
        coreBlock = new CoreBlock(shape, tint);
        connectors = ConnectionPlacer.getConnectors(coreBlock);
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
                        attach(dockingConnector);
    }

    //todo rework basing on some hierarchy of blocks.
    private Group attachedTo = null;
    private static int compensation = - (int) (BlockAppearance.padding * 0.8);

    void attach(ConnectionArea dockingConnector) {
        if (attachedTo == null && findActor(dockingConnector.parent.getParent().getName()) == null) {
            System.out.println("Attaching");
            attachedTo = dockingConnector.parent.getParent();
            setPosition(dockingConnector.direction.deltaX * (dockingConnector.parent.getWidth() + compensation),
                    dockingConnector.direction.deltaY * (dockingConnector.parent.getHeight() + compensation));
            attachedTo.addActor(this);
        }
    }

    void detach() {
        if (attachedTo != null) {
            System.out.println("Detaching");
            attachedTo.removeActor(this);
            moveBy(100,100); //todo some better shit
            attachedTo = null;
            AndroidGameGenerator.addToStage(this);
        }
    }
}
