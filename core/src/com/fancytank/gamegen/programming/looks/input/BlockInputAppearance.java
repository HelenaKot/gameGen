package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;
import com.fancytank.gamegen.programming.looks.PatchData;

abstract public class BlockInputAppearance {
    PatchData patchData;
    InputFragment inputFragment;
    CoreBlock parent;
    static int spacing = 10;

    BlockInputAppearance(InputFragment inputFragment, CoreBlock parent) {
        this.inputFragment = inputFragment;
        this.parent = parent;
        patchData = new PatchData(inputFragment.inputType.patch);
    }

    public void drawInput(Batch batch, float alpha) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }

    public abstract ConnectionArea getConnectors();

    public void setPosition(float x, float y) {
        patchData.startX = x;
        patchData.startY = y;
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
