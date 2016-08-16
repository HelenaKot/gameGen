package com.fancytank.gamegen.programming.blocks;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;

import org.greenrobot.eventbus.EventBus;

public class BlockActorPattern {
    String name;
    BlockData blockData;
    Color tint;

    public BlockActorPattern(String name, BlockData blockData, Color tint) {
        this.name = name;
        this.blockData = blockData;
        this.tint = tint;
    }

    public String getName() {
        return name;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public Color getColor() {
        return tint;
    }

    public BlockActorPattern setValue(String value, ValueType valueType) {
        setValue(new Variable(value, valueType));
        return this;
    }

    public BlockActorPattern setValue(Variable value) {
        blockData.setVariable(value);
        blockData.getInputs()[0].labelText = value.getValue();
        return this;
    }

    public void setExpectedValue(int inputNo, ValueType type) {
        if (inputNo > blockData.getInputs().length)
            System.out.println("Input out of bounds on pattern\n" + blockData.getInputs()[0].toString());
        else blockData.getInputs()[inputNo].setExpectedValue(type);
    }

    public BlockActorPattern setLabel(String text) {
        blockData.getInputs()[0].labelText = text;
        return this;
    }

    public void spawn() {
        BlockCreateEvent bce = new BlockCreateEvent(this);
        EventBus.getDefault().post(bce);
    }
}
