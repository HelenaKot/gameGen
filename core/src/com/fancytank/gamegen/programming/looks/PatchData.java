package com.fancytank.gamegen.programming.looks;

import com.badlogic.gdx.graphics.g2d.NinePatch;

public class PatchData {
    public NinePatch patch;
    public float startX, startY, width, height;

    public PatchData(NinePatch patch) {
        this.patch = patch;
    }

    void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    void setPosition(float startX, float startY) {
        this.startX = startX;
        this.startY = startY;
    }
}