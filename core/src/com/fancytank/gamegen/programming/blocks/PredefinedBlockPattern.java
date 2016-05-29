package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.looks.input.InputType;

public enum PredefinedBlockPattern implements BlockActorPattern {
    TEXT_VARIABLE(new CustomBlockPattern("variable", new BlockData(new InputFragment[]{new InputFragment(InputType.DUMMY, "NOPE")}, BlockShape.VARIABLE), Color.YELLOW)),
    PRINT(new CustomBlockPattern("print", new BlockData(new InputFragment[]{new InputFragment(InputType.VARIABLE, "Print")}, BlockShape.VARIABLE), Color.LIME));

    public final CustomBlockPattern pattern;

    PredefinedBlockPattern(CustomBlockPattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getName() {
        return pattern.name;
    }

    @Override
    public BlockData getBlockData() {
        return pattern.blockData;
    }

    @Override
    public Color getColor() {
        return pattern.tint;
    }
}
