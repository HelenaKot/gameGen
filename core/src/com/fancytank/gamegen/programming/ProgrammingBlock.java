package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
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
        System.out.println("attaching");
        ProgrammingBlock attachingBlock = getProgrammingBlock(dockingConnector), baseBlock = getProgrammingBlock(baseConnector);
        Vector2 dockingPosition = baseConnector.localToStageCoordinates(new Vector2(baseConnector.getX(), baseConnector.getY())),
                attachingPosition = dockingConnector.localToStageCoordinates(new Vector2(dockingConnector.getX(), dockingConnector.getY()));
        attachingBlock.attachedTo = baseBlock;
        attachingBlock.setPosition(baseConnector.getX() * 2 - dockingConnector.getX() * 2, //+ attachingBlock.getHeight(),
                baseConnector.getY() * 2 -  dockingConnector.getY() * 2);//+ attachingBlock.getHeight());
        System.out.println(attachingBlock.getX() + " : " + attachingBlock.getY());
       /* attachingBlock.setPosition(baseConnector.direction.deltaX * (baseConnector.coreBlock.getWidth() + compensation),
                baseConnector.direction.deltaY * (baseConnector.coreBlock.getHeight() + compensation));*/
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
