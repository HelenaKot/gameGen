package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.InputFragment;

import java.util.ArrayList;
import java.util.List;

import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getPatch;

public class BlockAppearance {
    //Label label;
    List<PatchData> patches;
    private int centerHeight, centerWidth;
    private InputFragment[] inputs;
    public static int padding = 51;
    static BitmapFont font;
    private static int top = Direction.UP.ordinal(), left = Direction.LEFT.ordinal(), down = Direction.DOWN.ordinal();
    private static Direction[] faces = new Direction[]{Direction.UP, Direction.LEFT, Direction.DOWN};

    BlockAppearance(CoreBlock root) {
        //label = new Label(labelText, new Label.LabelStyle(font, Color.BLACK));
        patches = new ArrayList<PatchData>();
        inputs = root.getInputs();

        for (Direction dir : faces)
            patches.add(new PatchData(getPatch(root.data.shape.connects(dir), dir)));

        centerHeight = 50;
        centerWidth = 300;
        createInputs();
        setSize();
        setPosition(0, 0);
    }

    private void setPosition(float x, float y) {
        patches.get(down).setPosition(x, y);
        patches.get(left).setPosition(x, y + padding);
        if (inputs != null) {
            if (inputs.length > 0)
                patches.get(faces.length).setPosition(x + padding, y + padding);
            for (int input = faces.length + 1; input < inputs.length + faces.length; input++) {
                patches.get(input).setPosition(x + padding + patches.get(input - 1).height, y + padding + patches.get(input - 1).width);
            }
        }
        //patch[RIGHT.ordinal()].setPosition(x + padding, y + padding);
        patches.get(top).setPosition(x, y + centerHeight + padding);
        //label.setPosition(x + padding, y + padding);
    }

    private void setSize() {
        centerWidth = 0;
        centerHeight = 0;

        if (inputs != null)
            for (int input = faces.length; input < inputs.length + faces.length; input++) {
                patches.get(input).setSize(300, 70);
                // todo placeholder
                centerWidth += patches.get(input).width - padding;
                centerHeight += patches.get(input).height;
            }
        patches.get(top).setSize(centerWidth + padding * 2, padding);
        patches.get(left).setSize(padding, centerHeight);
        //patch[RIGHT.ordinal()].setSize(centerWidth + padding, centerHeight);
        patches.get(down).setSize(centerWidth + padding * 2, padding);
    }

    private void createInputs() {
        if (inputs != null)
            for (InputFragment inputLine : inputs)
                patches.add(new PatchData(inputLine.inputType.patch));
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
        for (PatchData patch : patches)
            drawPatch(patch, batch);
        //label.draw(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }
}
