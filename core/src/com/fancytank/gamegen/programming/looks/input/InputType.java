package com.fancytank.gamegen.programming.looks.input;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.fancytank.gamegen.programming.Direction;

import java.io.IOException;
import java.io.Serializable;

import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getPatch;
import static com.fancytank.gamegen.programming.looks.PatchTextureManager.getSocket;

public enum InputType {
    DUMMY(getPatch(false, Direction.RIGHT)),
    VARIABLE(getPatch(true, Direction.RIGHT)),
    SOCKET(getSocket());

    transient public final NinePatch patch;

    InputType(NinePatch patch) {
        this.patch = patch;
    }
}