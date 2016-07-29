package com.fancytank.gamegen.game.script;

import com.fancytank.gamegen.game.script.ExecutableProducer.ActionListenerType;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.data.Variable;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.Vector;

public class Util {
    static ExecutableProducer createSubBlock(BlockData blockData) {
        if (blockData != null) {
            return new ExecutableProducer(blockData, ActionListenerType.NONE);
        }
        return null;
    }

    public static Vector<Variable> collectVars(BlockData methodBlock) {
        Vector<Variable> vars = new Vector<Variable>();
        InputFragment[] inputs = methodBlock.getInputs();
        for (int i = 0; i < inputs.length; i++)
            if (inputs[i].inputType != InputType.DUMMY)
                vars.add((inputs[i].connectedTo != null) ? inputs[i].connectedTo.getVariable() : new Variable("0", ValueType.ANY));
        return vars;
    }

    public static String getSum(Variable var0, Variable var1) {
        float value = Float.parseFloat(var0.getValue()) + Float.parseFloat(var1.getValue());
        return (var0.valueType == ValueType.INT_NUMBER) ? Integer.toString((int) value) : Float.toString(value);
    }

}