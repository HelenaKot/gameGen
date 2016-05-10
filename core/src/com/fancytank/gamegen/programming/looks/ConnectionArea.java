package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fancytank.gamegen.programming.Direction;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class ConnectionArea extends Actor {
    public CoreBlock coreBlock;
    public Direction direction;
    private Vector2 pos;

    public ConnectionArea(float x, float y, CoreBlock coreBlock, Direction direction) {
        this.coreBlock = coreBlock;
        this.direction = direction;
        this.setBounds(x / 2, y / 2, padding, padding);
    }

    public Rectangle getBoundingBox() {
        pos = localToStageCoordinates(new Vector2(getX(), getY()));
        return new Rectangle(pos.x, pos.y, padding, padding);
    }

    static private boolean projectionMatrixSet = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    //todo debug

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        pos = localToStageCoordinates(new Vector2(getX(), getY()));

        if (!projectionMatrixSet) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(pos.x, pos.y, getWidth(), getHeight());
        shapeRenderer.end();
        batch.begin();
    }

}
