package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;

import java.util.ArrayList;

import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getPatch;

public class BlockAppearance {
    PatchData[] patches;
    ArrayList<BlockInputAppearance> inputs;
    public static int padding = 51;
    public static BitmapFont font;
    private int height = 0, width = 0;
    private static int top = Direction.UP.ordinal(), left = Direction.LEFT.ordinal(), down = Direction.DOWN.ordinal();
    private static Direction[] faces = new Direction[]{Direction.UP, Direction.LEFT, Direction.DOWN};

    BlockAppearance(CoreBlock root) {
        patches = new PatchData[3];

        for (Direction dir : faces)
            patches[dir.ordinal()] = new PatchData(getPatch(root.data.shape.connects(dir), dir));

        createInputs(root.getInputs());
        setSize();
        setPosition(0, 0);
    }

    private void setPosition(float x, float y) {
        patches[down].setPosition(x, y);
        patches[left].setPosition(x, y + padding);
        if (inputs != null)
            setInputsPosition(x, y);
        patches[top].setPosition(x, y + height + padding);
    }

    private void setInputsPosition(float x, float y) {
        inputs.get(0).setPosition(x + padding, y + padding);
        for (int i = 1; i < inputs.size(); i++)
            inputs.get(i).setPosition(x + padding, inputs.get(i - 1).getHeight() + inputs.get(i - 1).getY());
    }

    private void setSize() {
        if (inputs != null)
            setInputsSize();
        patches[top].setSize(width + padding, padding);
        patches[left].setSize(padding, height);
        patches[down].setSize(width + padding, padding);
    }

    private void setInputsSize() {
        for (BlockInputAppearance input : inputs) {
            if (width < input.getWidth()) width = (int) input.getWidth();
            height += (int) input.getHeight();
        }
        unifyWidth();
    }

    private void unifyWidth() {
        for (BlockInputAppearance input : inputs)
            input.setWidth(width);
    }

    private void createInputs(InputFragment[] inputs) {
        if (inputs != null) {
            this.inputs = new ArrayList<BlockInputAppearance>();
            for (InputFragment inputLine : inputs)
                this.inputs.add(new BlockInputAppearance(inputLine));
        }
    }

    public static void loadFont(BitmapFont bitmapFont) {
        font = bitmapFont;
    }

    float getHeight() {
        return height + 2 * padding;
    }

    float getWidth() {
        return width + 2 * padding;
    }

    void drawShape(Batch batch, float alpha) {
        for (PatchData patch : patches)
            drawPatch(patch, batch);
        for (BlockInputAppearance input : inputs)
            input.drawInput(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }
}
