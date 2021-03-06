package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.blocks.ProgrammingBlock;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.data.VariableList;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;
import com.fancytank.gamegen.programming.looks.input.InputType;

import static com.fancytank.gamegen.programming.looks.Constant.padding;

public class ConnectionArea extends Actor {
    public final CoreBlock coreBlock;
    public Direction direction;
    public BlockInputAppearance blockInputAppearance;
    private ConnectionArea connectedTo;
    private InputFragment inputFragment;

    public ConnectionArea(float x, float y, CoreBlock coreBlock, Direction direction) {
        this.coreBlock = coreBlock;
        setUpArea(x, y, direction);
    }

    public ConnectionArea(float x, float y, BlockInputAppearance blockInputAppearance, Direction direction) {
        this.blockInputAppearance = blockInputAppearance;
        this.coreBlock = blockInputAppearance.coreBlock;
        setUpArea(x, y, direction);
    }

    public ValueType getAcceptedValueType() {
        if (inputFragment != null) return inputFragment.expectedValue;
        else if (direction == Direction.DOWN) return ValueType.METHOD;
        return ValueType.ANY;
    }

    public ValueType getInnerValueType() {
        if (direction == Direction.UP) return ValueType.METHOD;
        Variable var = coreBlock.data.getVariable();
        if (coreBlock.data.hasValue())
            return (var.valueType == ValueType.VARIABLE) ? VariableList.get(var.getDirectValue()).valueType : var.valueType;
        return ValueType.ANY;
    }

    private void setUpArea(float x, float y, Direction direction) {
        this.direction = direction;
        this.setBounds(x, y, padding, padding);
    }

    public void setInputFragment(InputFragment inputFragment) {
        if (this.inputFragment != inputFragment) {
            this.inputFragment = inputFragment;
            inputFragment.setConnectionArea(this);
        }
    }

    public boolean hasInputType() {
        return inputFragment != null;
    }

    public InputType getInputType() {
        if (hasInputType())
            return inputFragment.inputType;
        return null;
    }

    public Rectangle getBoundingBox() {
        Vector2 pos = getPosition();
        return new Rectangle(pos.x, pos.y, padding, padding);
    }

    public void connect(ConnectionArea connectedTo) {
        this.connectedTo = connectedTo;
        connectedTo.connectedTo = this;
        if (hasInputType())
            connectInput(connectedTo);
        else if (connectedTo.hasInputType())
            connectedTo.connectInput(this);
    }

    private void connectInput(ConnectionArea connection) {
        inputFragment.connectedTo = connection.coreBlock.data;
    }

    public void disconnect() {
        disconnectInput();
        connectedTo.connectedTo = null;
        this.connectedTo = null;
    }

    private void disconnectInput() {
        if (connectedTo.hasInputType())
            connectedTo.inputFragment.connectedTo = null;
        else if (hasInputType())
            inputFragment.connectedTo = null;
    }

    public ConnectionArea getConnection() {
        return connectedTo;
    }

    public boolean hasConnection() {
        return connectedTo != null;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        if (connectedTo != null && connectedTo.isOutputBlock())
            translateConnectedToInput();
    }

    private boolean isOutputBlock() {
        return this == coreBlock.programmingBlock.getFirstConnector();
    }

    private void translateConnectedToInput() {
        float deltaY = this.getPosition().y - connectedTo.getPosition().y;
        ProgrammingBlock block = connectedTo.coreBlock.programmingBlock;
        block.setPosition(block.getX(), block.getY() + deltaY);
    }

    private Vector2 getPosition() {
        return Utility.myLocalToStageCoordinates(this);
    }

    private static Texture debugTexture;
    public static boolean debug = false;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (debug) {
            if (debugTexture == null)
                debugTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
            batch.draw(debugTexture, getX(), getY(), getWidth(), getHeight());
        }
    }
}
