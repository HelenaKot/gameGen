package com.fancytank.generated.game.programming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ProgrammingBlock {
    Color tint;
    Label label;
    PatchData topPatch, leftPatch, rightPatch, bottomPatch;
    int x, y;
    static Skin skin;
    static int padding = 16;

    ProgrammingBlock(String labelText, BlockShape shape) {
        label = new Label(labelText, skin);

        topPatch = new PatchData(getPatchTexture(shape.connects(Direction.UP), Direction.UP));
        leftPatch = new PatchData(getPatchTexture(shape.connects(Direction.LEFT), Direction.LEFT));
        rightPatch = new PatchData(getPatchTexture(shape.connects(Direction.RIGHT), Direction.RIGHT));
        bottomPatch = new PatchData(getPatchTexture(shape.connects(Direction.DOWN), Direction.DOWN));

        setSize();
        setPosition(0, 0);
    }

    void setSize() {
        topPatch.setSize(label.getWidth() + padding * 2, padding);
        leftPatch.setSize(padding, label.getHeight());
        rightPatch.setSize(label.getWidth() + padding, label.getHeight());
        bottomPatch.setSize(label.getWidth() + padding * 2, padding);
    }

    void setPosition(int x, int y) {
        bottomPatch.setPosition(x, y);
        leftPatch.setPosition(x, y + bottomPatch.height);
        rightPatch.setPosition(x + leftPatch.width, y + bottomPatch.height);
        topPatch.setPosition(x, y + leftPatch.startY + leftPatch.height);
        label.setPosition(x + padding, y + padding);
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

    public static void loadSkin(Skin uiskin) {
        skin = uiskin;
    }

    private NinePatch getPatchTexture(boolean isConnected, Direction direction) {
        if (isConnected)
            return BlockTextureManager.connected[direction.ordinal()];
        else
            return BlockTextureManager.plain[direction.ordinal()];
    }

    class PatchData {
        NinePatch patch;
        float startX, startY, width, height;

        PatchData(NinePatch patch) {
            this.patch = patch;
        }

        void setSize(float width, float height) {
            this.width = width;
            this.height = height;
        }

        void setPosition(float startX, float startY) {
            this.startX = startX;
            this.startY = startY;
        }
    }
}
