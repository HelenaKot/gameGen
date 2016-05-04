package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.AndroidGameGenerator;

import java.util.ArrayList;

public class ProgrammingBlock extends Group {
    BlockActor coreBlock;
    ArrayList<ConnectionArea> connectors;
    private float touchedX, touchedY;
    private static ArrayList<ProgrammingBlock> blocksList = new ArrayList<ProgrammingBlock>();

    public ProgrammingBlock(BlockShape shape, Color tint) {
        coreBlock = new BlockActor(shape, tint);
        connectors = ConnectionPlacer.getConnectors(coreBlock);
        blocksList.add(this);
        setBounds(0, 0, coreBlock.getWidth(), coreBlock.getWidth());

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
                        attach(localConnector, dockingConnector);
    }

    void attach(ConnectionArea localConnector, ConnectionArea dockingConnector) {
        System.out.println("connected!");
        float deltaX = dockingConnector.getBoundingBox().x - localConnector.getBoundingBox().x;
        float deltaY = dockingConnector.getBoundingBox().y - localConnector.getBoundingBox().y;
        moveBy(deltaX, deltaY);
    }

    void detach() {
        System.out.println("dosconected!");
    }
}
