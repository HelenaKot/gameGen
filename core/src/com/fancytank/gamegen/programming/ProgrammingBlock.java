package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.AndroidGameGenerator;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;
import com.fancytank.gamegen.programming.looks.InputType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.UUID;

import static com.fancytank.gamegen.programming.looks.ConnectionPlacer.getConnectors;

public class ProgrammingBlock extends Group {
    CoreBlock coreBlock;
    public ArrayList<ConnectionArea> connectors;
    private float touchedX, touchedY;
    private Group attachedTo = null;
    private static ArrayList<ProgrammingBlock> blocksList = new ArrayList<ProgrammingBlock>();
    private static ProgrammingBlock detachingBlock;

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

    public ConnectionArea getOutputConnector() {
        return connectors.get(0);
    }

    private void setUpListeners() {
        final ProgrammingBlock local = this;
        addListener(new InputListener() {
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
                    detachingBlock.moveBy(x - detachingBlock.getX() + getX() - detachingBlock.touchedX, // + detachingX - touchedX - detachingBlock.touchedX,
                            y - detachingBlock.getY() + getY() - detachingBlock.touchedY);//+ detachingY - touchedY - detachingBlock.touchedY);
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

    void tryAttach(ConnectionArea localConnector, ConnectionArea dockingConnector) {
        ProgrammingBlock block = getProgrammingBlock(dockingConnector);
        if (attachedTo == null && findActor(block.getName()) == null) {
            if (this.getSignificance() < block.getSignificance())
                attachBlock(localConnector, dockingConnector);
            else
                attachBlock(dockingConnector, localConnector);
        }
    }

    static void attachBlock(ConnectionArea dockingConnector, ConnectionArea baseConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(dockingConnector), baseBlock = getProgrammingBlock(baseConnector);
        attachingBlock.setPosition(baseConnector.getX() * 2 - dockingConnector.getX() * 2,
                baseConnector.getY() * 2 - dockingConnector.getY() * 2);

        setDependencies(attachingBlock, baseBlock);
        dockingConnector.connect(baseConnector);

        sendConnectionEvent(baseConnector, dockingConnector, true);
    }

    static private void setDependencies(ProgrammingBlock attachingBlock, ProgrammingBlock baseBlock) {
        attachingBlock.attachedTo = baseBlock;
        baseBlock.addActor(attachingBlock);
    }

    // TODO Resize only on socket! link ConnectionArea with inputs plz
    static private void sendConnectionEvent(ConnectionArea baseConnector, ConnectionArea dockingConnector, boolean isConnecting) {
        if (baseConnector.getInputType() == InputType.SOCKET)
            EventBus.getDefault().post(new BlockResizeEvent(baseConnector, dockingConnector, isConnecting));
        else
            EventBus.getDefault().post(new BlockConnectionEvent(baseConnector, dockingConnector, isConnecting));
    }

    boolean detach() {
        if (attachedTo != null) {
            tryDisconnectOutput();
            this.setPosition(attachedTo.getX() + this.getX(), attachedTo.getY() + this.getY());
            AndroidGameGenerator.addToStage(this);
            removeDependencies();
            detachingBlock = this;
            return false;
        }
        return true;
    }

    private void tryDisconnectOutput() {
        ConnectionArea outputConnector = getOutputConnector();
        if (outputConnector.getConnectedTo() != null) {
            sendConnectionEvent(outputConnector.getConnectedTo(), outputConnector, false);
            outputConnector.disconnect();
        }
    }

    private void removeDependencies() {
        attachedTo.removeActor(this);
        attachedTo = null;
    }

    private int getSignificance() {
        return this.coreBlock.data.shape.significance;
    }

    private static ProgrammingBlock getProgrammingBlock(ConnectionArea connectionArea) {
        return connectionArea.coreBlock.getParent();
    }
}
