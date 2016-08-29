package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.MainGdx;
import com.fancytank.gamegen.programming.BlockResizeEvent;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.TrashCan;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;
import com.fancytank.gamegen.programming.looks.Utility;
import com.fancytank.gamegen.programming.looks.input.InputType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.UUID;

import static com.fancytank.gamegen.programming.looks.ConnectionPlacer.getConnectors;
import static com.fancytank.gamegen.programming.looks.Utility.getProgrammingBlock;

public class ProgrammingBlock extends Group {
    public CoreBlock coreBlock;
    public ArrayList<ConnectionArea> connectors;
    private float touchedX, touchedY;
    private Group attachedTo = null;
    private int myZIndex;

    public ProgrammingBlock(BlockData data, Color tint) {
        setName(UUID.randomUUID().toString());
        coreBlock = new CoreBlock(this, data, tint);
        data.setCoreBlock(coreBlock);
        connectors = getConnectors(coreBlock);
        setBounds(0, 0, coreBlock.getWidth(), coreBlock.getHeight());
        setUpListeners();
        populateGroup();
    }

    public ConnectionArea getFirstConnector() {
        return connectors.get(0);
    }

    public ConnectionArea getDownFacingConnector() {
        for (ConnectionArea connectionArea : connectors)
            if (connectionArea.direction == Direction.DOWN)
                return connectionArea;
        return null;
    }

    private void setUpListeners() {
        final ProgrammingBlock local = this;
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedX = x;
                touchedY = y;
                detach();

                setMyZIndex(local.getZIndex());
                local.setZIndex(100);

                event.setBubbles(false);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (ProgrammingBlock programmingBlock : BlockManager.getBlocList())
                    if (programmingBlock != local)
                        checkOverlapping(programmingBlock);
                if (checkBinOverlapping())
                    destroy();
                local.setZIndex(myZIndex);

                event.setBubbles(false);
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - touchedX, y - touchedY);
                checkBinOverlapping();
                event.setBubbles(false);
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

    private boolean checkBinOverlapping() {
        if (coreBlock.getBoundingBox().overlaps(TrashCan.instance.getBoundingBox()))
            return TrashCan.instance.setHover(true);
        else
            return TrashCan.instance.setHover(false);
    }

    private void checkConnectors(ProgrammingBlock programmingBlock) {
        for (ConnectionArea localConnector : connectors)
            for (ConnectionArea baseConnector : programmingBlock.connectors)
                if (localConnector.getBoundingBox().overlaps(baseConnector.getBoundingBox()))
                    if (localConnector.direction == baseConnector.direction.flip())
                        ConnectionRules.tryConnect(localConnector, baseConnector);
    }

    static void attachBlock(ConnectionArea baseConnector, ConnectionArea attachingConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(attachingConnector);
        attachingBlock.setPosition(baseConnector.getX() - attachingConnector.getX(), baseConnector.getY() - attachingConnector.getY());
        setDependencies(baseConnector, attachingConnector);
        sendConnectionEvent(baseConnector, attachingConnector, true);
    }

    static private void setDependencies(ConnectionArea baseConnector, ConnectionArea attachingConnector) {
        ProgrammingBlock attachingBlock = getProgrammingBlock(attachingConnector), baseBlock = getProgrammingBlock(baseConnector);
        attachingBlock.attachedTo = baseBlock;
        baseBlock.addActor(attachingBlock);
        attachingConnector.connect(baseConnector);
        if (!baseConnector.hasInputType()) {
            BlockData descendantBlock = attachingBlock.coreBlock.data, parentBlock = baseBlock.coreBlock.data;
            descendantBlock.setParent(parentBlock);
        }
    }

    static private void sendConnectionEvent(ConnectionArea baseConnector, ConnectionArea dockingConnector, boolean isConnecting) {
        if (baseConnector.getInputType() == InputType.SOCKET) {
            BlockResizeEvent event = new BlockResizeEvent(baseConnector, dockingConnector, isConnecting);
            event.setLast(true);
            EventBus.getDefault().post(event);
        }
        if (baseConnector.getInputType() != InputType.VARIABLE)
            sendConnectionEventUpward(baseConnector.coreBlock, isConnecting);
    }

    static private void sendConnectionEventUpward(CoreBlock block, boolean isConnecting) {
        ConnectionArea outputConnector = block.getProgrammingBlock().getFirstConnector();
        if (outputConnector.hasConnection() && outputConnector.getConnection().getInputType() == InputType.SOCKET) {
            EventBus.getDefault().post(new BlockResizeEvent(outputConnector.getConnection(), outputConnector, isConnecting));
        }
        if (block.data.hasParent())
            sendConnectionEventUpward(block.data.getParent().getCoreBlock(), isConnecting);
        if (shouldSendToSocketUpward(outputConnector))
            sendConnectionEventUpward(outputConnector.getConnection().coreBlock, isConnecting);
    }

    private static boolean shouldSendToSocketUpward(ConnectionArea outputConnector) {
        return outputConnector.hasConnection()
                && outputConnector.getConnection().hasInputType()
                && outputConnector.getConnection().getInputType() == InputType.SOCKET;
    }

    private void detach() {
        if (attachedTo != null) {
            Vector2 pos = Utility.myLocalToStageCoordinates(this);
            setPosition(pos.x, pos.y);
            MainGdx.addToStage(this);
            removeDependencies();
        }
    }

    private void removeDependencies() {
        attachedTo.removeActor(this);
        attachedTo = null;
        tryDisconnectOutput();
    }

    private void tryDisconnectOutput() {
        ConnectionArea outputConnector = getFirstConnector();
        if (outputConnector.hasConnection()) {
            if (outputConnector.direction == Direction.UP && coreBlock.data.hasParent())
                coreBlock.data.removeParent();
            ConnectionArea tmp = outputConnector.getConnection();
            outputConnector.disconnect();
            sendConnectionEvent(tmp, outputConnector, false);
        }
    }

    public void destroy() {
        BlockManager.getBlocList().remove(this);
        if (coreBlock.data.hasDescendant())
            coreBlock.data.getDescendant().getCoreBlock().getProgrammingBlock().destroy();
        for (InputFragment input : coreBlock.data.getInputs())
            if (input.hasConnectionArea() && input.getConnectionArea().hasConnection())
                input.getConnectionArea().getConnection().coreBlock.getProgrammingBlock().destroy();
        this.remove();
        TrashCan.instance.setHover(false);
    }

    private void setMyZIndex(int z) {
        myZIndex = z;
    }

    public int getSignificance() {
        return this.coreBlock.data.shape.significance;
    }
}
