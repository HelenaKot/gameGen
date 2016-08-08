package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.fancytank.gamegen.programming.BlockResizeEvent;
import com.fancytank.gamegen.programming.Direction;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.input.BlockInputAppearance;
import com.fancytank.gamegen.programming.looks.input.BlockInputFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getMiniPatch;
import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getPatch;

public class BlockAppearance {
    PatchData[] patches;
    ArrayList<BlockInputAppearance> inputs;
    public int padding = Constant.padding;
    public static BitmapFont font;
    private int height, width;
    private static int top = Direction.UP.ordinal(), left = Direction.LEFT.ordinal(), down = Direction.DOWN.ordinal();
    private static Direction[] faces = new Direction[]{Direction.UP, Direction.LEFT, Direction.DOWN};

    BlockAppearance(CoreBlock root) {
        patches = new PatchData[3];
        if (root.data.shape == BlockShape.VARIABLE) {
            padding = Constant.slimPadding;
            for (Direction dir : faces)
                patches[dir.ordinal()] = new PatchData(getMiniPatch(dir));
        } else
            for (Direction dir : faces)
                patches[dir.ordinal()] = new PatchData(getPatch(root.data.shape.connects(dir), dir));

        createInputs(root);
        setSize();
        setPosition(0, 0);

        EventBus.getDefault().register(this);
    }

    public static void loadFont(BitmapFont bitmapFont) {
        font = bitmapFont;
    }

    @Subscribe
    public void onEvent(BlockResizeEvent event) {
        if (event.getBaseBlockAppearance() == this)
            BlockResizer.resizeBlock(event);
    }

    void updateSize() {
        setSize();
        setPosition(patches[down].startX, patches[down].startY);
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
        height = 0;
        width = 0;
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

    private void createInputs(CoreBlock root) {
        if (root.getInputs() != null) {
            inputs = new ArrayList<>();
            for (InputFragment inputLine : root.getInputs())
                inputs.add(BlockInputFactory.create(inputLine, root));
            Collections.reverse(inputs);
        }
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
