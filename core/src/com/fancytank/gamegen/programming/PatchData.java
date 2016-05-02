package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.g2d.NinePatch;

class PatchData {
    NinePatch patch;
    float startX, startY, width, height;

    PatchData(NinePatch patch) {
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