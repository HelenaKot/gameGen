package com.fancytank.generated.game.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlockActor extends Actor {
    Color tint = Color.YELLOW;
    ProgrammingBlock pb;

    public BlockActor(BlockShape shape) {
        pb = new ProgrammingBlock("le Placeholder", shape);
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        pb.drawShape(batch, alpha);
    }

}
