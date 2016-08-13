package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.CoreBlock;
import com.fancytank.gamegen.programming.looks.PatchData;
import com.fancytank.gamegen.programming.looks.PatchTextureManager;

abstract public class BlockInputAppearance {
    PatchData patchData;
    InputFragment inputFragment;
    public final CoreBlock coreBlock;

    BlockInputAppearance(InputFragment inputFragment, CoreBlock coreBlock) {
        this.inputFragment = inputFragment;
        this.coreBlock = coreBlock;
        patchData = getPatchData();
    }

    private PatchData getPatchData() {
        if (inputFragment.inputType == InputType.VARIABLE && coreBlock.data.shape == BlockShape.VARIABLE)
            return new PatchData(PatchTextureManager.getMiniVariabla());
        else
            return new PatchData(inputFragment.inputType.patch);
    }

    public void drawInput(Batch batch, float alpha) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }

    public abstract ConnectionArea getConnector();

    public abstract Vector2 getConnectorPlacement();

    public void setPosition(float x, float y) {
        patchData.startX = x;
        patchData.startY = y;
    }

    public float getWidth() {
        return patchData.width;
    }

    public void setWidth(float width) {
        patchData.width = width;
    }

    public float getHeight() {
        return patchData.height;
    }

    public void setHeight(float height) {
        patchData.height = height;
    }

    public float getY() {
        return patchData.startY;
    }

}
