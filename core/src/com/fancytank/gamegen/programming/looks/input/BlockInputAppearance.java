package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockAppearance;
import com.fancytank.gamegen.programming.looks.PatchData;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class BlockInputAppearance {
    Label label;
    PatchData patchData;
    InputFragment inputFragment;
    private static int spacing = 10;

    public BlockInputAppearance(InputFragment inputFragment) {
        this.inputFragment = inputFragment;
        patchData = new PatchData(inputFragment.inputType.patch);
        //if (inputFragment.inputType != InputType.SOCKET)
        label = new Label(inputFragment.labelText, new Label.LabelStyle(BlockAppearance.font, Color.BLACK));
        setPreferredSize();
    }

    public void drawInput(Batch batch, float alpha) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
        label.draw(batch, alpha);
    }

    void setPreferredSize() {
        patchData.width = label.getWidth() + padding;
        patchData.height = label.getHeight() + spacing;
    }

    public void setPosition(float x, float y) {
        patchData.startX = x;
        patchData.startY = y;
        label.setPosition(x + spacing / 2, y);
    }

    public float getHeight() {
        return patchData.height;
    }

    public float getWidth() {
        return patchData.width;
    }

    public void setWidth(float width) {
        patchData.width = width;
    }

    public float getY() {
        return patchData.startY;
    }

}
