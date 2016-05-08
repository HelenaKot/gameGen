package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fancytank.gamegen.programming.Direction;

import java.util.ArrayList;
import java.util.List;

import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getPatch;

public class BlockAppearance {
    //Label label;
    List<PatchData> patches;
    private int centerHeight, centerWidth;
    public static int padding = 51;
    static BitmapFont font;
    private int top = Direction.UP.ordinal(), left = Direction.LEFT.ordinal(), down = Direction.DOWN.ordinal();
    private Direction[] faces = new Direction[]{Direction.UP, Direction.LEFT, Direction.DOWN};

    BlockAppearance(CoreBlock root) {
        //label = new Label(labelText, new Label.LabelStyle(font, Color.BLACK));
        patches = new ArrayList<PatchData>();

        for (Direction dir : faces)
            patches.add(new PatchData(getPatch(root.data.shape.connects(dir), dir)));

        centerHeight = 50;
        centerWidth = 300;
        setSize();
        setPosition(0, 0);
    }

    private void setPosition(float x, float y) {
        patches.get(down).setPosition(x, y);
        patches.get(left).setPosition(x, y + padding);
        //patch[RIGHT.ordinal()].setPosition(x + padding, y + padding);
        patches.get(top).setPosition(x, y + centerHeight + padding);
        //label.setPosition(x + padding, y + padding);

    }

    private void setSize() {
        patches.get(top).setSize(centerWidth + padding * 2, padding);
        patches.get(left).setSize(padding, centerHeight);
        //patch[RIGHT.ordinal()].setSize(centerWidth + padding, centerHeight);
        patches.get(down).setSize(centerWidth + padding * 2, padding);
    }

    public static void loadFont(BitmapFont bitmapFont) {
        font = bitmapFont;
    }

    float getHeight() {
        return centerHeight + 2 * padding;
    }

    float getWidth() {
        return patches.get(top).width;
    }

    void drawShape(Batch batch, float alpha) {
        for (Direction dir : faces)
            drawPatch(patches.get(dir.ordinal()), batch);
        //label.draw(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }
}
