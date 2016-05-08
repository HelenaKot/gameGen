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
    private int height = 0, width = 0;
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

        createInputs();
        setSize();
        setPosition(0, 0);
    }

    private void setPosition(float x, float y) {
        patches.get(down).setPosition(x, y);
        patches.get(left).setPosition(x, y + padding);
        if (inputs != null)
            setInputsPosition(x, y);
        patches.get(top).setPosition(x, y + height + padding);
        //label.setPosition(x + padding, y + padding);
    }

    private void setInputsPosition(float x, float y) {
        if (inputs.length > 0)
            patches.get(faces.length).setPosition(x + padding, y + padding);
        for (int input = faces.length + 1; input < inputs.length + faces.length; input++) {
            patches.get(input).setPosition( x + padding, patches.get(input - 1).height + patches.get(input - 1).startY);
        }
    }

    private void setSize() {
        if (inputs != null)
            setInputsSize();
        patches.get(top).setSize(width + padding, padding);
        patches.get(left).setSize(padding, height);
        patches.get(down).setSize(width + padding, padding);
    }

    private void setInputsSize() {
        for (int input = faces.length; input < inputs.length + faces.length; input++) {
            patches.get(input).setSize(300, 70);
            width = (int) patches.get(input).width; //+ padding;
            height += patches.get(input).height; //+ padding * 2;
        }
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
        return height + 2 * padding;
    }

    float getWidth() {
        return width + 2 * padding;
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
