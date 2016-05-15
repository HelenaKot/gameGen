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
        data.setCoreBlock(coreBlock);
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
                detach();
                return true;
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
            for (ConnectionArea baseConnector : programmingBlock.connectors)
                if (localConnector.getBoundingBox().overlaps(baseConnector.getBoundingBox()))
                    if (localConnector.direction == baseConnector.direction.flip())
                        ConnectionRules.tryConnect(localConnector, baseConnector);
    }

    static void attachBlock(ConnectionArea attachingConnector, ConnectionArea baseConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(attachingConnector), baseBlock = getProgrammingBlock(baseConnector);
        attachingBlock.setPosition(baseConnector.getX() * 2 - attachingConnector.getX() * 2, baseConnector.getY() * 2 - attachingConnector.getY() * 2);
        setDependencies(attachingConnector, baseConnector);
        sendConnectionEvent(baseConnector, attachingConnector, true);
    }

    static private void setDependencies(ConnectionArea attachingConnector, ConnectionArea baseConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(attachingConnector), baseBlock = getProgrammingBlock(baseConnector);
        attachingBlock.attachedTo = baseBlock;
        baseBlock.addActor(attachingBlock);
        attachingConnector.connect(baseConnector);
        BlockData descendantBlock = attachingBlock.coreBlock.data, parentBlock = baseBlock.coreBlock.data;
        descendantBlock.setParent(parentBlock);
    }

    static private void sendConnectionEvent(ConnectionArea baseConnector, ConnectionArea dockingConnector, boolean isConnecting) {
        if (baseConnector.getInputType() == InputType.SOCKET)
            EventBus.getDefault().post(new BlockResizeEvent(baseConnector, dockingConnector, isConnecting));
        else
            EventBus.getDefault().post(new BlockConnectionEvent(baseConnector, dockingConnector, isConnecting));
        if (baseConnector.getInputType() != InputType.VARIABLE)
            sendConnectionEventUpward(baseConnector.coreBlock, isConnecting);
    }

    static private void sendConnectionEventUpward(CoreBlock block, boolean isConnecting) {
        ConnectionArea outputConnector = block.getParent().getOutputConnector();
        if (outputConnector.hasConnection() && outputConnector.getConnection().getInputType() == InputType.SOCKET) {
            EventBus.getDefault().post(new BlockResizeEvent(outputConnector.getConnection(), outputConnector, isConnecting));
        }
        if (block.data.hasParent())
            sendConnectionEventUpward(block.data.getParent().getCoreBlock(), isConnecting);
    }

    private boolean detach() {
        if (attachedTo != null) {
            this.setPosition(attachedTo.getX() + this.getX(), attachedTo.getY() + this.getY());
            AndroidGameGenerator.addToStage(this);
            removeDependencies();
            detachingBlock = this;
            return false;
        }
        return true;
    }

    private void removeDependencies() {
        tryDisconnectOutput();
        attachedTo.removeActor(this);
        attachedTo = null;
        coreBlock.data.removeParent();
    }

    private void tryDisconnectOutput() {
        ConnectionArea outputConnector = getOutputConnector();
        if (outputConnector.getConnection() != null) {
            sendConnectionEvent(outputConnector.getConnection(), outputConnector, false);
            outputConnector.disconnect();
        }
    }

    int getSignificance() {
        return this.coreBlock.data.shape.significance;
    }

    static ProgrammingBlock getProgrammingBlock(ConnectionArea connectionArea) {
        return connectionArea.coreBlock.getParent();
    }
}
