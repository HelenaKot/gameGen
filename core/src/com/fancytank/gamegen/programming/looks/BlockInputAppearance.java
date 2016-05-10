package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.fancytank.gamegen.programming.data.InputFragment;

public class BlockInputAppearance {
    PatchData patchData;
    InputFragment inputFragment;

    BlockInputAppearance(InputFragment inputFragment) {
        this.inputFragment = inputFragment;
        patchData = new PatchData(inputFragment.inputType.patch);
    }

    public void drawInput(Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }

    void setSize(float width, float height) {
        patchData.width = width;
        patchData.height = height;
    }

    void setPosition (float x, float y) {
        patchData.startX = x;
        patchData.startY = y;
    }

    float getHeight() {
        return patchData.height;
    }

    float getWidth() {
        return patchData.width;
    }

    float getY() {
        return patchData.startY;
    }

}
