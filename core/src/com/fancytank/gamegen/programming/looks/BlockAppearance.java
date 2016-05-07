package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fancytank.gamegen.programming.Direction;

import static com.fancytank.gamegen.programming.Direction.*;
import static com.fancytank.gamegen.programming.looks.PatchTextureManager.*;

public class BlockAppearance {
    Label label;
    PatchData[] patch = new PatchData[4];
    static BitmapFont font;
    public static int padding = 51;

    BlockAppearance(CoreBlock root, String labelText) {
        label = new Label(labelText, new Label.LabelStyle(font, Color.BLACK));

        for (Direction dir : values())
            patch[dir.ordinal()] = new PatchData(getPatch(root.data.shape.connects(dir), dir));

        setSize();
        setPosition(0, 0);
    }

    public static void loadFont(BitmapFont bitmapFont) {
        font = bitmapFont;
    }

    float getHeight() {
        return patch[RIGHT.ordinal()].height + 2 * padding;
    }

    float getWidth() {
        return patch[UP.ordinal()].width;
    }

    private void setPosition(float x, float y) {
        patch[DOWN.ordinal()].setPosition(x, y);
        patch[LEFT.ordinal()].setPosition(x, y + padding);
        patch[RIGHT.ordinal()].setPosition(x + padding, y + padding);
        patch[UP.ordinal()].setPosition(x, y + label.getHeight() + padding);
        label.setPosition(x + padding, y + padding);
    }

    private void setSize() {
        patch[UP.ordinal()].setSize(label.getWidth() + padding * 2, padding);
        patch[LEFT.ordinal()].setSize(padding, label.getHeight());
        patch[RIGHT.ordinal()].setSize(label.getWidth() + padding, label.getHeight());
        patch[DOWN.ordinal()].setSize(label.getWidth() + padding * 2, padding);
    }

    private void initCenter() {

    }

    void drawShape(Batch batch, float alpha) {
        for (Direction dir : values())
            drawPatch(patch[dir.ordinal()], batch);
        label.draw(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }
}
