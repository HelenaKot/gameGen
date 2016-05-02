package com.fancytank.generated.game.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Block extends Actor {
    Color tint = Color.YELLOW;
    ProgrammingBlock pb;

    public Block(BlockShape shape, Skin skinFile) {
       pb = new ProgrammingBlock("le Placeholder", BlockShape.VARIABLE);
    }

    public void draw(Batch batch, float alpha) {
        batch.setColor(tint);
        pb.drawShape(batch,alpha);
    }

}
