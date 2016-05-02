package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ProgrammingBlock {
    Label label;
    PatchData topPatch, leftPatch, rightPatch, bottomPatch;
    static Skin skin;
    static int padding = 16;

    ProgrammingBlock(Actor root, String labelText, BlockShape shape) {
        label = new Label(labelText, skin);

        topPatch = new PatchData(getPatchTexture(shape.connects(Direction.UP), Direction.UP));
        leftPatch = new PatchData(getPatchTexture(shape.connects(Direction.LEFT), Direction.LEFT));
        rightPatch = new PatchData(getPatchTexture(shape.connects(Direction.RIGHT), Direction.RIGHT));
        bottomPatch = new PatchData(getPatchTexture(shape.connects(Direction.DOWN), Direction.DOWN));

        setSize(root);
        setPosition(0, 0);
    }

    public static void loadSkin(Skin uiSkin) {
        skin = uiSkin;
    }

    void setPosition(float x, float y) {
        bottomPatch.setPosition(x, y);
        leftPatch.setPosition(x, y + bottomPatch.height);
        rightPatch.setPosition(x + leftPatch.width, y + bottomPatch.height);
        topPatch.setPosition(x, leftPatch.startY + leftPatch.height);
        label.setPosition(x + padding, y + padding);
    }

    void translate(float x, float y) {
        setPosition(bottomPatch.startX + x, bottomPatch.startY + y);
    }

    private void setSize(Actor root) {
        topPatch.setSize(label.getWidth() + padding * 2, padding);
        leftPatch.setSize(padding, label.getHeight());
        rightPatch.setSize(label.getWidth() + padding, label.getHeight());
        bottomPatch.setSize(label.getWidth() + padding * 2, padding);
        root.setBounds(0, 0, label.getWidth() + padding * 2, label.getHeight() + padding * 2);
    }

    void drawShape(Batch batch, float alpha) {
        drawPatch(topPatch, batch);
        drawPatch(leftPatch, batch);
        drawPatch(rightPatch, batch);
        drawPatch(bottomPatch, batch);
        label.draw(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }

    private NinePatch getPatchTexture(boolean isConnected, Direction direction) {
        if (isConnected)
            return BlockTextureManager.connected[direction.ordinal()];
        else
            return BlockTextureManager.plain[direction.ordinal()];
    }
}
