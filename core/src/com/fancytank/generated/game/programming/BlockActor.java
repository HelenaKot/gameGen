package com.fancytank.generated.game.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BlockActor extends Actor {
    Color tint = Color.YELLOW;
    ProgrammingBlock blockAppearance;

    public BlockActor(BlockShape shape) {
        blockAppearance = new ProgrammingBlock(this, "le Placeholder", shape);
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x, y);
                System.out.println("MOVING " + x + ' ' + y);
            }
        });
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        // todo przesuwaj nie lewy gorny rog, tylko miejsce za ktore sie ciagnie
        super.moveBy(deltaX, deltaY);
        blockAppearance.translate(deltaX, deltaY);
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        blockAppearance.drawShape(batch, alpha);
    }

}
