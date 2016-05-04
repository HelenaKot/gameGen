package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.fancytank.gamegen.programming.BlockAppearance.padding;

public class ConnectionArea extends Actor {
    Actor parent;
    Direction direction;

    private Rectangle boundingBox;

    ConnectionArea(float x, float y, BlockActor parent, Direction direction) {
        this.parent = parent;
        this.direction = direction;
        boundingBox = new Rectangle(x, y, padding, padding);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    static private boolean projectionMatrixSet = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    //todo debug
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        if (!projectionMatrixSet) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        boundingBox.setPosition(boundingBox.getX() + deltaX, boundingBox.getY() + deltaY);
    }
}
