package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.ProgrammingBlock;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class ConnectionArea extends Actor {
    public final CoreBlock coreBlock;
    public Direction direction;
    public BlockInputAppearance blockInputAppearance;
    private ConnectionArea connectedTo;

    public ConnectionArea(float x, float y, CoreBlock coreBlock, Direction direction) {
        this.coreBlock = coreBlock;
        this.direction = direction;
        this.setBounds(x / 2, y / 2, padding, padding);
    }

    public ConnectionArea(float x, float y, BlockInputAppearance blockInputAppearance, Direction direction) {
        this.blockInputAppearance = blockInputAppearance;
        this.coreBlock = blockInputAppearance.coreBlock;
        this.direction = direction;
        this.setBounds(x / 2, y / 2, padding, padding);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        if (connectedTo != null && connectedTo.isOutputBlock())
            translateConnectedToInput();
    }

    private boolean isOutputBlock() {
        return this == coreBlock.parent.getOutputConnector();
    }

    private void translateConnectedToInput() {
        float deltaY = this.getPosition().y - connectedTo.getPosition().y;
        System.out.println(deltaY + "  -delta-  ");
        ProgrammingBlock block = connectedTo.coreBlock.parent;
        System.out.println(block.getX() + "  -pos-  " + block.getY());
        //todo
        block.setPosition(block.getX(), block.getY() + deltaY);
    }

    public Rectangle getBoundingBox() {
        Vector2 pos = getPosition();
        return new Rectangle(pos.x, pos.y, padding, padding);
    }

    public void connect(ConnectionArea connectedTo) {
        this.connectedTo = connectedTo;
        connectedTo.connectedTo = this;
    }

    public void disconnect() {
        connectedTo.connectedTo = null;
        this.connectedTo = null;
    }

    private Vector2 getPosition() {
        return localToStageCoordinates(new Vector2(getX(), getY()));
    }

    public ConnectionArea getConnectedTo() {
        return connectedTo;
    }

    static private boolean projectionMatrixSet = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    //todo debug

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        Vector2 pos = getPosition();
        batch.end();
        if (!projectionMatrixSet) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(pos.x, pos.y, getWidth(), getHeight());
        shapeRenderer.end();
        batch.begin();
        */
    }
}
